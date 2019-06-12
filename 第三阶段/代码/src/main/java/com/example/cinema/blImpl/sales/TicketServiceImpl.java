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

import java.text.SimpleDateFormat;
import java.util.*;

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
			tickets.forEach(ticket -> {
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
	private void lockSeat(OrderForm orderForm) {
		try {
			List<Ticket> tickets = orderForm.getTicketPOs();
			ticketMapper.insertTickets(tickets);

			////////////////////控制台测试信息////////////////////
			System.out.print("锁座：");
			for (SeatForm seat : orderForm.getSeats()) {
				System.out.print(" " + (seat.getRowIndex() + 1) + "排" + (seat.getColumnIndex() + 1) + "列");
			}
			System.out.println();
			////////////////////控制台测试信息////////////////////
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see #lockSeat(OrderForm)
	 */
	@Override
	@Transactional
	public ResponseVO addTicket(OrderForm orderForm) {
		try {
			// 锁座
			this.lockSeat(orderForm);

			// 计算总金额
			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(orderForm.getScheduleId());
			double total = scheduleItem.getFare() * orderForm.getSeats().size();

			// 用户拥有的、且满足本次订单使用门槛的优惠券
			List<Coupon> couponsOwnedByUser = couponService.getCouponsByUserAndAmount(orderForm.getUserId(), total);

			// 根据座位生成ticketVO数组
			List<TicketVO> ticketVOList = new ArrayList<>();
			for (SeatForm seat : orderForm.getSeats()) {
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

	@Override
	public ResponseVO proceedWithOrder(int orderId) {
		try {
			List<Ticket> tickets = ticketMapper.selectTicketsByOrder(orderId);
			List<TicketVO> ticketVOList = Ticket.ticketList2TicketVOList(tickets);

			// 计算总金额
			Ticket firstTicket = tickets.get(0);
			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(firstTicket.getScheduleId());
			double total = scheduleItem.getFare() * tickets.size();

			// 用户拥有的、且满足本次订单使用门槛的优惠券
			List<Coupon> couponsOwnedByUser = couponService.getCouponsByUserAndAmount(firstTicket.getUserId(), total);

			// 返回的TicketWithCouponVO
			TicketWithCouponVO vo = new TicketWithCouponVO();
			vo.setTicketVOList(ticketVOList);
			vo.setTotal(total);
			vo.setCoupons(couponsOwnedByUser);

			return ResponseVO.buildSuccess(vo);
		} catch(Exception e) {
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

	private ResponseVO completeTickets(List<Integer> ticketIdList, int couponId, boolean byVIPCard) {
        try {
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

            if (success && byVIPCard) { // 未超时且使用会员卡支付，尝试扣款
                // 判断扣款是否成功
                success = vipService.pay(firstTicket.getUserId(), actualPayment);
            }

            if (success) { // 未超时且扣款成功
                for (int ticketId : ticketIdList) {
                    ticketMapper.updateTicketState(ticketId, 1); // 更新电影票状态为“已完成”
                    ticketMapper.updateTicketActualPayment(
                            ticketId,
                            actualPayment / ticketIdList.size() // 平均每张票实际付款
                    );
                }

                // 删除用过的优惠券
                if (!noCoupon) {
                    couponService.deleteCouponUser(coupon.getId(), firstTicket.getUserId());
                }

                // 尝试赠送优惠券
                activityService.participate(scheduleItem.getMovieId(), firstTicket.getUserId());

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
	@Transactional
	public ResponseVO completeByCreditCard(List<Integer> ticketIdList, int couponId) {
	    return this.completeTickets(ticketIdList, couponId, false);
	}

	@Override
	@Transactional
	public ResponseVO completeByVIPCard(List<Integer> ticketIdList, int couponId) {
	    return this.completeTickets(ticketIdList, couponId, true);
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
			System.out.println(strategyForm.getRefundable()+" "+strategyForm.getRatio()+" "+strategyForm.getTime());

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
		try {
			Ticket ticket = ticketMapper.selectTicketById(ticketId);
			int scheduleId = ticket.getScheduleId();

			ScheduleItem scheduleItem = scheduleService.getScheduleItemById(scheduleId);
			int movieId = scheduleItem.getMovieId();

			TicketRefundStrategy ticketRefundStrategy
					= ticketMapper.selectRefundStrategyByMovie(movieId);

			// 如果该电影票有对应的退票策略，则返回该退票策略
			if(ticketRefundStrategy!=null) {
				return ResponseVO.buildSuccess(ticketRefundStrategy.getVO());  // 将允许退票的电影票对应的退票策略返回，由前端计算可退还给用户的金额
			} else {
				return ResponseVO.buildFailure("电影票没有对应的退票策略，无法进行退票");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("选择指定要退票的电影票失败");
		}
	}

	@Override
	public ResponseVO completeRefundTicket(int ticketId) {
		try {
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
	public ResponseVO getOrdersByUser(int userId) {
		try {
			List<OrderVO> orders = new ArrayList<>();
			List<Ticket> tickets = ticketMapper.selectTicketsByUser(userId);

			int tempOrderId = -1;
			OrderVO tempOrder = null;
			for(Ticket ticket : tickets) {
				if(ticket.getOrderId() != tempOrderId) { // 下一个订单
					if(tempOrder != null) {
						orders.add(tempOrder); // 前一个订单完成
					}

					tempOrder = new OrderVO();

					tempOrder.setOrderId(ticket.getOrderId());
					tempOrderId = ticket.getOrderId();

					tempOrder.setUserId(userId);
					tempOrder.setSeatVOList(new ArrayList<>());

					ScheduleItem scheduleItem
							= scheduleService.getScheduleItemById(ticket.getScheduleId());
					tempOrder.setSchedule(scheduleItem);

					tempOrder.setTime(ticket.getTime());
					tempOrder.setState(ticket.getStateString());
					// TODO setActualPayment
				}
				tempOrder.getSeatVOList().add(ticket.getSeatVO());
			}
			orders.add(tempOrder); // 最后一个订单

			return ResponseVO.buildSuccess(orders);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取用户订单失败");
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

	@Override
	public ResponseVO searchAllRefundStrategy() {
		try {
			List<TicketRefundStrategyVO> ticketRefundStrategyVOList = new ArrayList<>();
			ticketMapper.selectRefundStrategies().forEach(ticketRefundStrategy -> {
				ticketRefundStrategyVOList.add(new TicketRefundStrategyVO(ticketRefundStrategy));
			});
			return ResponseVO.buildSuccess(ticketRefundStrategyVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取已有的所有退票策略失败");
		}
	}

	@Override
	public ResponseVO getMoviesNotInRefundStrategy() {
		try {
			List<MovieVO> movieVOList = new ArrayList<>();
			ticketMapper.selectMovieNotInRefundStrategy().forEach(
					movie -> {
						movieVOList.add(new MovieVO(movie));
					}
			);
			return ResponseVO.buildSuccess(movieVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取未被指定退票策略的电影列表失败");
		}
	}

	@Override
	public ResponseVO getMoviesByRefundStrategy(int strategyId) {
		try {
			List<MovieVO> movieVOList = new ArrayList<>();
			ticketMapper.selectRefundStrategyById(strategyId).getMovieList().forEach(
					movie -> {
						movieVOList.add(new MovieVO(movie));
					}
			);
			return ResponseVO.buildSuccess(movieVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取使用指定退票策略的电影列表失败");
		}
	}
}
