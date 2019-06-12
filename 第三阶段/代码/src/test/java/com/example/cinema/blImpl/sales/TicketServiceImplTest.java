package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.OrderForm;
import com.example.cinema.vo.ScheduleWithSeatVO;
import com.example.cinema.vo.SeatForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //导入spring测试框架
@SpringBootTest  //提供spring依赖注入
@Transactional  //事务管理，默认回滚,如果配置了多数据源记得指定事务管理器
@DisplayName("Test TicketService")
class TicketServiceImplTest {
    @Autowired
    TicketService ticketService;

    @Test
    void addTicket() {
        OrderForm order = new OrderForm();
        order.setUserId(16);
        order.setScheduleId(87);

        SeatForm seatOne = new SeatForm(6, 4);
        SeatForm seatTwo = new SeatForm(7, 4);
        List<SeatForm> seats = new ArrayList<>();
        seats.add(seatOne);
        seats.add(seatTwo);

        order.setSeats(seats);
        ticketService.addTicket(order);

        int[][] seatsAfter =
                ((ScheduleWithSeatVO) ticketService
                        .getBySchedule(87)
                        .getContent())
                .getSeats();

        assertEquals(seatsAfter[4][7], 0);
    }

    @Test
    void proceedWithOrder() {
    }

    @Test
    void completeByCreditCard() {
    }

    @Test
    void completeByVIPCard() {
    }

    @Test
    void pickUpTicket() {
    }

    @Test
    void addRefundStrategy() {
    }

    @Test
    void updateRefundStrategy() {
    }

    @Test
    void addRefundMovies() {
    }

    @Test
    void removeRefundMovies() {
    }

    @Test
    void getRefundStrategies() {
    }

    @Test
    void addRefundTicket() {
    }

    @Test
    void completeRefundTicket() {
    }

    @Test
    void getTicketsByUser() {
    }

    @Test
    void getOrdersByUser() {
    }

    @Test
    void getMoviesNotInRefundStrategy() {
    }

    @Test
    void getMoviesInRefundStrategy() {
    }
}