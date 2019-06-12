package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.OrderForm;
import com.example.cinema.vo.ScheduleWithSeatVO;
import com.example.cinema.vo.SeatForm;
import com.example.cinema.vo.TicketWithScheduleVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DisplayName("Test TicketService")
class TicketServiceImplTest {

    @Autowired
    TicketService ticketService;

    private static final int orderId = 20190613;
    private static final int userId = 16;
    private static final int scheduleId = 90;
    private static OrderForm order;

    // 获得用户的所有电影票
    private List<TicketWithScheduleVO> getTickets() {
        return (List<TicketWithScheduleVO>)
                ticketService.getTicketsByUser(userId).getContent();
    }

    // 获得订单号为 20190613 的所有电影票
    private List<TicketWithScheduleVO> getTicketsByOrder() {
        return ((List<TicketWithScheduleVO>)
                ticketService.getTicketsByUser(userId).getContent())
                .stream()
                .filter(ticket -> ticket.getOrderId() == orderId)
                .collect(Collectors.toList());
    }

    // 获得订单号为 20190613 的所有电影票的id列表
    private List<Integer> getTicketIdList() {
        List<TicketWithScheduleVO> tickets = this.getTicketsByOrder();
        return tickets.stream()
                .map(ticket->ticket.getId())
                .collect(Collectors.toList());
    }

    // 获得订单号为 20190613 的所有电影票的状态列表
    private List<String> getTicketStateList() {
        List<TicketWithScheduleVO> tickets = this.getTicketsByOrder();
        return tickets.stream()
                .map(ticket -> ticket.getState())
                .collect(Collectors.toList());
    }

    // 下单 20190613
    private void addOrder() {
        ticketService.addTicket(TicketServiceImplTest.order);
    }

    // 支付单 20190613
    private void completeOrder() {
        this.addOrder();

        // 获得订单的电影票id
        List<Integer> ticketIdList = this.getTicketIdList();

        // 银行卡支付
        ticketService.completeByCreditCard(ticketIdList, -1);
    }

    // ================================================================================

    @BeforeAll
    static void setUpOrder() {
        OrderForm order = new OrderForm();
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setScheduleId(scheduleId);

        SeatForm seatOne = new SeatForm(5, 5);
        SeatForm seatTwo = new SeatForm(6, 6);
        List<SeatForm> seats = new ArrayList<>();
        seats.add(seatOne);
        seats.add(seatTwo);
        order.setSeats(seats);

        TicketServiceImplTest.order = order;
    }

    @Test
    void addTicket() {
        this.addOrder();

        int[][] seatsAfter =
                ((ScheduleWithSeatVO) ticketService
                        .getBySchedule(scheduleId)
                        .getContent())
                .getSeats();

        assertEquals(1, seatsAfter[5][5]);
        assertEquals(1, seatsAfter[6][6]);
    }

    @Test
    void proceedWithOrder() {
        this.addOrder();

        ticketService.proceedWithOrder(orderId);
        List<TicketWithScheduleVO> tickets = this.getTicketsByOrder();
        for(TicketWithScheduleVO ticket : tickets) {
            assertEquals("支付未完成", ticket.getState());
        }
    }

    @Test
    void completeByCreditCard() {
        this.addOrder();

        // 获得订单的电影票id
        List<Integer> ticketIdList = this.getTicketIdList();

        // 银行卡支付
        ticketService.completeByCreditCard(ticketIdList, -1);

        // 获得订单中电影票的状态
        List<String> stateList = this.getTicketStateList();

        for(String state : stateList) {
            assertEquals("支付已完成", state);
        }
    }

    @Test
    void completeByVIPCard() {
        this.addOrder();

        // 获得订单的电影票id
        List<Integer> ticketIdList = this.getTicketIdList();

        // 银行卡支付
        ticketService.completeByVIPCard(ticketIdList, -1);

        // 获得订单中电影票的状态
        List<String> stateList = this.getTicketStateList();

        for(String state : stateList) {
            assertEquals("支付未完成", state);
        }
    }

    @Test
    void pickUpTicket() {
        this.completeOrder();

        List<Integer> ticketIdList = this.getTicketIdList();
        ticketService.pickUpTicket(ticketIdList.get(0));
        List<String> ticketStateList = this.getTicketStateList();
        assertEquals("已出票", ticketStateList.get(0));
    }
}