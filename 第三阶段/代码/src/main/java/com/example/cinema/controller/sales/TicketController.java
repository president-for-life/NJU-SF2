package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.OrderForm;
import com.example.cinema.vo.TicketRefundStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/16
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/vip/buy")
    public ResponseVO buyTicketByVIPCard(@RequestBody List<Integer> ticketId, @RequestParam int couponId) {
        return ticketService.completeByVIPCard(ticketId, couponId);
    }

    @PostMapping("/lockSeat")
    public ResponseVO lockSeat(@RequestBody OrderForm orderForm) {
        return ticketService.addTicket(orderForm);
    }

    @PostMapping("/buy")
    public ResponseVO buyTicket(@RequestBody List<Integer> ticketId, @RequestParam int couponId) {
        return ticketService.completeByCreditCard(ticketId, couponId);
    }

    @PostMapping("/proceed")
    public ResponseVO proceedWithOrder(@RequestParam int orderId) {
        return ticketService.proceedWithOrder(orderId);
    }

    @GetMapping("/get/{userId}")
    public ResponseVO getTicketsByUserId(@PathVariable int userId) {
        return ticketService.getTicketsByUser(userId);
    }

    @GetMapping("/get/order/{userId}")
    public ResponseVO getOrdersByUserId(@PathVariable int userId) {
        return ticketService.getOrdersByUser(userId);
    }

    @GetMapping("/get/occupiedSeats")
    public ResponseVO getOccupiedSeats(@RequestParam int scheduleId) {
        return ticketService.getBySchedule(scheduleId);
    }

    @GetMapping("/pickUp")
    public ResponseVO pickUpTicket(@RequestParam int ticketId){
        return ticketService.pickUpTicket(ticketId);
    }

    @PostMapping("/refundStrategy/publish")
    public ResponseVO publishRefundStrategy(@RequestBody TicketRefundStrategyForm ticketRefundStrategyForm) {
        System.out.println("apublishRefundStrategy()");
        return ticketService.addRefundStrategy(ticketRefundStrategyForm);
    }

    @PostMapping("/refundStrategy/update")
    public ResponseVO updateRefundStrategy(@RequestBody TicketRefundStrategyForm ticketRefundStrategyForm) {
        System.out.println("updateRefundStrategy()");
        return ticketService.updateRefundStrategy(ticketRefundStrategyForm);
    }

    @PostMapping("/refundStrategy/addMovies")
    public ResponseVO addRefundMovies(@RequestParam int refundStrategyId, @RequestBody List<Integer> movieIdList) {
        System.out.println("addRefundMovies()");
        return ticketService.addRefundMovies(refundStrategyId, movieIdList);
    }

    @PostMapping("/refundStrategy/removeMovies")
    public ResponseVO removeRefundMovies(@RequestParam int refundStrategyId, @RequestBody List<Integer> movieIdList) {
        System.out.println("removeRefundMovies()");
        return ticketService.removeRefundMovies(refundStrategyId, movieIdList);
    }

    @PostMapping("/refundStrategy/get")
    public ResponseVO removeRefundMovies() {
        return ticketService.getRefundStrategies();
    }

    @PostMapping("/refund/choose")
    public ResponseVO chooseRefundTicket(@RequestParam int ticketId) {
        System.out.println("chooseRefundTicket()");
        return ticketService.addRefundTicket(ticketId);
    }

    @PostMapping("/refund/confirm")
    public ResponseVO confirmRefundTicket(@RequestParam int ticketId) {
        System.out.println("confirmRefundTicket()");
        return ticketService.completeRefundTicket(ticketId);
    }

    @GetMapping("/refundStrategy/all")
    public ResponseVO searchAllRefundStrategy() {
        System.out.println("searchAllRefundStrategy()");
        return ticketService.searchAllRefundStrategy();
    }

    @GetMapping("/refundStrategy/getSelectableMovies")
    public ResponseVO getMoviesNotInRefundStrategy() {
        System.out.println("getMoviesNotInRefundStrategy()");
        return ticketService.getMoviesNotInRefundStrategy();
    }

    @GetMapping("/refundStrategy/getMovies")
    public ResponseVO getMoviesByRefundStrategy(@RequestParam int strategyId) {
        System.out.println("getMoviesByRefundStrategy()");
        return ticketService.getMoviesByRefundStrategy(strategyId);
    }
}
