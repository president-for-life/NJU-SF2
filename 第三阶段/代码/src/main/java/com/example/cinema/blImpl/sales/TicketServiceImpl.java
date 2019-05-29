package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.ActivityServiceForBl;
import com.example.cinema.blImpl.promotion.CouponServiceForBl;
import com.example.cinema.blImpl.promotion.VIPServiceForBl;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李莹
 */
@Service
public class TicketServiceImpl implements TicketService, TicketServiceForBl {

	@Autowired
	TicketMapper ticketMapper;
	@Autowired
	ScheduleServiceForBl scheduleService;
	@Autowired
	HallServiceForBl hallService;
	@Autowired
	ActivityServiceForBl activityService;
	@Autowired
	CouponServiceForBl couponService;
	@Autowired
	VIPServiceForBl vipService;

	@Override
	public ResponseVO getBySchedule(int scheduleId) {
		try {
			// 获得某场次的所有电影票
			List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);

			// 得出该场次的被锁座位
			ScheduleItem schedule = scheduleService.getScheduleItemById(scheduleId);
			Hall hall = hallService.getHallById(schedule.getHallId());
			int[][] seats = new int[hall.getRow()][hall.getColumn()];
			tickets.stream().forEach(ticket -> {
				seats[ticket.getRowIndex()][ticket.getColumnIndex()] = 1;
			});

			ScheduleWithSeatVO scheduleWithSeatVO = new ScheduleWithSeatVO();
			scheduleWithSeatVO.setScheduleItem(schedule);
			scheduleWithSeatVO.setSeats(seats);
			return ResponseVO.buildSuccess(scheduleWithSeatVO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	/**
	 * 锁座，即增加票但状态为未付款
	 *
	 * @author 戴羽涵
	 */
	private void lockSeat(TicketForm ticketForm) {
		try {
			List<SeatForm> seats = ticketForm.getSeats();

			if (seats.size() == 1) { // 用户只选择一个座位
				Ticket ticket = new Ticket();
				ticket.setUserId(ticketForm.getUserId());
				ticket.setScheduleId(ticketForm.getScheduleId());
				ticket.setColumnIndex(seats.get(0).getColumnIndex());
				ticket.setRowIndex(seats.get(0).getRowIndex());
				ticket.setState(0);
				ticketMapper.insertTicket(ticket);
			} else { // 用户选择了多个座位
				List<Ticket> tickets = new ArrayList<>();
				for (SeatForm seat : seats) {
					Ticket ticket = new Ticket();
					ticket.setUserId(ticketForm.getUserId());
					ticket.setScheduleId(ticketForm.getScheduleId());
					ticket.setColumnIndex(seat.getColumnIndex());
					ticket.setRowIndex(seat.getRowIndex());
					ticket.setState(0);
					tickets.add(ticket);
				}
				ticketMapper.insertTickets(tickets);
			}

			////////////////////控制台测试信息////////////////////
			System.out.print("锁座：");
			for (SeatForm seat : seats) {
				System.out.print(" " + (seat.getRowIndex() + 1) + "排" + (seat.getColumnIndex() + 1) + "列");
			}
			System.out.println("成功！");
			////////////////////控制台测试信息////////////////////
		} catch (Exception e) {
			e.printStackTrace();

			////////////////////控制台测试信息////////////////////
			System.out.println("锁座失败！");
			////////////////////控制台测试信息////////////////////
		}
	}

	/**
	 * 获得用户拥有的、且满足本次订单使用门槛的优惠券
	 *
	 * @param userId 用户id
	 * @param total  订单总金额
	 * @author 梁正川
	 */
	private List<Coupon> getCoupons(int userId, double total) {
		try {
			return couponService.getCouponsByUserForBl(userId)
					.stream()
					.filter(coupon -> coupon.getTargetAmount() <= total)
					.sorted(Comparator.comparing(Coupon::getDiscountAmount).reversed()) // 优惠金额多的排前面
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @see #lockSeat(TicketForm)
	 * @see #getCoupons(int, double)
	 */
	@Override
	@Transactional
	public ResponseVO addTicket(TicketForm ticketForm) {
		try {
			////////////////////控制台测试信息////////////////////
			System.out.println("----------TicketServiceImpl.addTicket测试信息----------");
			////////////////////控制台测试信息////////////////////

			// 锁座
			this.lockSeat(ticketForm);

			// 计算总金额
			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticketForm.getScheduleId());
			double total = scheduleItem.getFare() * ticketForm.getSeats().size();

			// 用户拥有的、且满足本次订单使用门槛的优惠券
			List<Coupon> couponsOwnedByUser = this.getCoupons(ticketForm.getUserId(), total);

			// 根据座位生成ticketVO数组
			List<TicketVO> ticketVOList = new ArrayList<>();
			for (SeatForm seat : ticketForm.getSeats()) {
				Ticket ticket = ticketMapper.selectTicketByScheduleIdAndSeat(
						scheduleItem.getId(),
						seat.getColumnIndex(),
						seat.getRowIndex()
				);
				ticketVOList.add(ticket.getVO());
			}

			// 返回的TicketWithCouponVO
			TicketWithCouponVO vo = new TicketWithCouponVO();
			vo.setTicketVOList(ticketVOList);
			vo.setTotal(total);
			vo.setCoupons(couponsOwnedByUser);

			return ResponseVO.buildSuccess(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

	/**
	 * 判断电影票是否已过期
	 *
	 * @author 梁正川
	 */
	private boolean areExpired(List<Integer> ticketIds) {
		try {
			// 确保超时的电影票状态为“已失效”
			ticketMapper.cleanExpiredTicket();

			// 只需查看一张电影票是否超时，就知道所有电影票是否失效
			Ticket firstTicket = ticketMapper.selectTicketById(ticketIds.get(0));

			return firstTicket.getState() == 2;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * 根据优惠活动、订单中的电影决定赠送优惠券
	 *
	 * @param movieId 订单中的电影id
	 * @param userId  用户id
	 * @author 梁正川
	 */
	private void issueCoupon(int movieId, int userId) {
		try {
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());

			// 当前正在进行的优惠活动
			List<Activity> activities = activityService.getActivitiesForBl()
					.stream()
					.filter(activity -> currentTime.after(activity.getStartTime())
							&& activity.getEndTime().after(currentTime))
					.collect(Collectors.toList());

			List<Coupon> couponsToBeIssued = new ArrayList<>(); // 赠送的优惠券
			for (Activity activity : activities) {
				// 活动参与条件是某时间段购买任意电影票
				if (activity.getMovieList() == null || activity.getMovieList().size() == 0) {
					couponsToBeIssued.add(activity.getCoupon());
				}
				// 活动参与条件是某时间段购买指定电影票
				else {
					// 享受优惠的电影id数组
					List<Integer> movies = activity.getMovieList()
							.stream()
							.map(Movie::getId)
							.collect(Collectors.toList());

					if (movies.contains(movieId)) {
						couponsToBeIssued.add(activity.getCoupon());
					}
				}
			}

			// 赠送优惠券
			for (Coupon coupon : couponsToBeIssued) {
				couponService.issueCoupon(coupon.getId(), Arrays.asList(userId));
			}

			////////////////////控制台测试信息////////////////////
			System.out.print("赠送优惠券：");
			for (Coupon coupon : couponsToBeIssued) {
				System.out.print(" 满" + coupon.getTargetAmount() + "送" + coupon.getDiscountAmount());
			}
			System.out.println();
			////////////////////控制台测试信息////////////////////
		} catch (Exception e) {
			e.printStackTrace();

			////////////////////控制台测试信息////////////////////
			System.out.println("赠送优惠券失败！");
			////////////////////控制台测试信息////////////////////
		}
	}

	/**
	 * @see #issueCoupon(int, int)
	 */
	@Override
	@Transactional
	public ResponseVO completeTicket(List<Integer> ticketIdList, int couponId) {
		////////////////////控制台测试信息////////////////////
		System.out.println("----------TicketServiceImpl.completeTicket测试信息----------");
		////////////////////控制台测试信息////////////////////

		// 根据第一张电影票就能够获取排片信息
		Ticket firstTicket = ticketMapper.selectTicketById(ticketIdList.get(0));
		ScheduleItem scheduleItem = scheduleService.getScheduleItemById(firstTicket.getScheduleId());

		Coupon coupon = couponService.getCouponById(couponId); // 使用的优惠券
		boolean noCoupon = (coupon == null); // 判断是否存在 couponId 对应的优惠券
		double actualPayment = scheduleItem.getFare() * ticketIdList.size()
				- (noCoupon ? 0 : coupon.getDiscountAmount());

		if (!this.areExpired(ticketIdList)) { // 电影票未超时
			for (int ticketId : ticketIdList) {
				ticketMapper.updateTicketState(ticketId, 1); // 更新电影票状态为“已完成”
				ticketMapper.updateTicketActualPayment(ticketId, actualPayment);
			}

			// 删除用过的优惠券
			if (!noCoupon) {
				couponService.deleteCouponUser(coupon.getId(), firstTicket.getUserId());
			}

			// 尝试赠送优惠券
			this.issueCoupon(scheduleItem.getMovieId(), firstTicket.getUserId());

			return ResponseVO.buildSuccess("会员卡购票成功");
		} else { // 电影票超时
			return ResponseVO.buildFailure("会员卡购票失败");
		}
	}

	/**
	 * @see #issueCoupon(int, int)
	 */
	@Override
	@Transactional
	public ResponseVO completeByVIPCard(List<Integer> ticketIdList, int couponId) {
		try {
			////////////////////控制台测试信息////////////////////
			System.out.println("----------TicketServiceImpl.completeByVIPCard测试信息----------");
			////////////////////控制台测试信息////////////////////

			// 根据第一张电影票就能够获取排片信息
			Ticket firstTicket = ticketMapper.selectTicketById(ticketIdList.get(0));
			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(firstTicket.getScheduleId());

			// 计算最终需要支付的金额
			Coupon coupon = couponService.getCouponById(couponId); // 使用的优惠券
			boolean noCoupon = (coupon == null); // 判断是否存在 couponId 对应的优惠券
			double actualPayment = scheduleItem.getFare() * ticketIdList.size()
					- (noCoupon ? 0 : coupon.getDiscountAmount());

			// 判断电影票是否超时
			boolean success = !this.areExpired(ticketIdList);

			if (success) { // 未超时，尝试扣款
				// 判断扣款是否成功
				success = vipService.pay(firstTicket.getUserId(), actualPayment);
			}

			if (success) { // 未超时且扣款成功
				for (int ticketId : ticketIdList) {
					ticketMapper.updateTicketState(ticketId, 1); // 更新电影票状态为“已完成”
					ticketMapper.updateTicketActualPayment(ticketId, actualPayment);
				}

				// 删除用过的优惠券
				if (!noCoupon) {
					couponService.deleteCouponUser(coupon.getId(), firstTicket.getUserId());
				}

				// 尝试赠送优惠券
				this.issueCoupon(scheduleItem.getMovieId(), firstTicket.getUserId());

				return ResponseVO.buildSuccess("会员卡购票成功");
			} else { // 电影票超时或扣款失败（会员卡余额不足）
				return ResponseVO.buildFailure("会员卡扣款失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("会员卡购票失败");
		}
	}

	@Override
	public ResponseVO pickUpTicket(int ticketId) {
		try {
			if (ticketMapper.selectTicketById(ticketId).getState() == 1) { // 支付已完成但未出票
				ticketMapper.updateTicketState(ticketId, 3); // 更改状态为“已出票”
				return ResponseVO.buildSuccess("取票成功");
			} else {
				return ResponseVO.buildFailure("电影票不满足取票条件，取票失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("取票失败");
		}
	}

	@Override
	public ResponseVO addRefundStrategy(TicketRefundStrategyForm strategyForm) {
		try {
			ticketMapper.insertOneRefundStrategy(strategyForm.getPO());
			return ResponseVO.buildSuccess("新增退票策略成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("新增退票策略失败");
		}
	}

	@Override
	public ResponseVO updateRefundStrategy(TicketRefundStrategyForm strategyForm) {
		try {
			ticketMapper.updateOneRefundStrategy(strategyForm.getPO());
			return ResponseVO.buildSuccess("修改退票策略成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("修改退票策略失败");
		}
	}

	@Override
	public ResponseVO addRefundMovies(int refundStrategyId, List<Integer> movieIdList) {
		try {
			ticketMapper.insertStrategyAndMovies(refundStrategyId, movieIdList);
			return ResponseVO.buildSuccess("添加指定退票策略的电影列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("添加指定退票策略的电影列表失败");
		}
	}

	@Override
	public ResponseVO removeRefundMovies(int refundStrategyId, List<Integer> movieIdList) {
		try {
			ticketMapper.deleteStrategyAndMovies(refundStrategyId, movieIdList);
			return ResponseVO.buildSuccess("删除指定退票策略的电影列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("删除指定退票策略的电影列表失败");
		}
	}

	@Override
	public ResponseVO addRefundTicket(int ticketId) {
		ticketMapper.lockTables(); // 锁数据库表，防止管理员修改退票策略

		try {
			Ticket ticket = ticketMapper.selectTicketById(ticketId);
			int scheduleId = ticket.getScheduleId();

			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(scheduleId);
			int movieId = scheduleItem.getMovieId();

			TicketRefundStrategy ticketRefundStrategy
					= ticketMapper.selectRefundStrategyByMovie(movieId);

			// 计算指定要退票的电影票所在场次的放映时间是否在允许退票的时间段
			Date movieStartDate = scheduleService.getScheduleItemById(scheduleId).getStartTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date presentDate = new Date();  // 获取当前日期时间
			long hourGap=(presentDate.getTime() - movieStartDate.getTime())/3600000;  // 毫秒 ==> 小时
			long minuteGap=(presentDate.getTime()-movieStartDate.getTime())/60000;  // 毫秒 ==> 分钟

			boolean canBeRefund =
					ticket.getState() == 1 && ticketRefundStrategy.getRefundable() && (minuteGap >= (long) ticketRefundStrategy.getTime());
			if (canBeRefund) {  // 如果满足所有的退票条件，就计算可退给用户的金额
//				double actualPayment = ticket.getActualPayment();  // 获取用户实际付款的金额
//				double refundPayment = actualPayment * ticketRefundStrategy.getRatio();  // 计算可退还给用户的金额
				return ResponseVO.buildSuccess(ticketRefundStrategy.getVO());  // 将允许退票的电影票对应的退票策略返回，由前端计算可退还给用户的金额
			} else {
				return ResponseVO.buildFailure("电影票不满足退票条件，无法选择");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("选择指定要退票的电影票失败");
		}
	}

	@Override
	public ResponseVO completeRefundTicket(int ticketId) {
		try {
			ticketMapper.unlockTables(); // 解锁数据库表，管理员现在开始可以修改退票策略
			if (ticketMapper.selectTicketById(ticketId).getState() == 1) { // 支付已完成但未出票
				ticketMapper.updateTicketState(ticketId, 4); // 更改状态为“已退票”
				return ResponseVO.buildSuccess("退票成功");
			} else {
				return ResponseVO.buildFailure("电影票不满足退票条件，无法退票");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("退票失败");
		}
	}

	@Override
	public ResponseVO getTicketsByUser(int userId) {
		try {
			List<Ticket> ticketList = ticketMapper.selectTicketsByUser(userId);
			List<TicketWithScheduleVO> ticketWithScheduleVOList = new ArrayList<>();
			for (Ticket ticket : ticketList) {
				TicketWithScheduleVO ticketWithScheduleVO = ticket.getWithScheduleVO();
				ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticket.getScheduleId());
				ticketWithScheduleVO.setSchedule(scheduleItem); // 设置vo中的排片
				ticketWithScheduleVOList.add(ticketWithScheduleVO);
			}
			return ResponseVO.buildSuccess(ticketWithScheduleVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取用户买过的票失败");
		}
	}

	@Override
	public List<Ticket> getTicketsByUserForBl(int userId) {
		try {
			return ticketMapper.selectTicketsByUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
