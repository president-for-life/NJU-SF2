package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.ActivityServiceForBl;
import com.example.cinema.blImpl.promotion.CouponServiceForBl;
import com.example.cinema.blImpl.promotion.VIPServiceForBl;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

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
	@Transactional
	public ResponseVO addTicket(TicketForm ticketForm) {
		return null;
	}

	@Override
	@Transactional
	public ResponseVO completeTicket(List<Integer> id, int couponId) {
// TODO: 2019-05-04 ticket的时间怎么创建？
		try {
			// 根据用户选择的座位，创建新的票，可能不止一张
			List<SeatForm> seats = ticketForm.getSeats();

			// 如果用户只选择了一张票
			if (seats.size() == 1) {
				Ticket ticket = new Ticket();
				ticket.setUserId(ticketForm.getUserId());
				ticket.setScheduleId(ticketForm.getScheduleId());
				ticket.setColumnIndex(seats.get(0).getColumnIndex());
				ticket.setRowIndex(seats.get(0).getRowIndex());
				ticket.setState(0);
				ticketMapper.insertTicket(ticket);
			} else {
				// 如果用户选择了多张票
				List<Ticket> tickets = new ArrayList<>();
				seats.forEach(seat -> {
					Ticket ticket = new Ticket();
					ticket.setUserId(ticketForm.getUserId());
					ticket.setScheduleId(ticketForm.getScheduleId());
					ticket.setColumnIndex(seat.getColumnIndex());
					ticket.setRowIndex(seat.getRowIndex());
					ticket.setState(0);
					tickets.add(ticket);
				});
				ticketMapper.insertTickets(tickets);
			}
			return ResponseVO.buildSuccess("锁座成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("锁座失败");
		}
	}

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

	@Override
	public ResponseVO getTicketByUser(int userId) {
		try {
			return ResponseVO.buildSuccess(ticketMapper.selectTicketByUser(userId).stream().map(Ticket::getVO));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("获取用户买过的票失败");
		}
	}

	@Override
	@Transactional
	public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
		return null;
	}

	@Override
	public ResponseVO cancelTicket(List<Integer> id) {
		try {
			ticketIds.forEach(ticketId -> {
				Ticket ticket = ticketMapper.selectTicketById(ticketId);
				// 只取消正在锁座中的"座位"
				// TODO: 2019-05-03 如果这个ticketIds列表中，有的ticket的状态是0，有的是1，那怎么办呢？
				if (ticket.getState() == 0) {
					ticketMapper.deleteTicket(ticketId);
				}
			});
			return ResponseVO.buildSuccess("取消锁座成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("取消锁座失败");
		}
	}


}
