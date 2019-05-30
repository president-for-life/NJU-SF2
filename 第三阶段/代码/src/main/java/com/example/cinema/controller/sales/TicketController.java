package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
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
    public ResponseVO lockSeat(@RequestBody TicketForm ticketForm) {
        return ticketService.addTicket(ticketForm);
    }

    @PostMapping("/buy")
    public ResponseVO buyTicket(@RequestBody List<Integer> ticketId, @RequestParam int couponId) {
        return ticketService.completeTicket(ticketId, couponId);
    }

    @GetMapping("/get/{userId}")
    public ResponseVO getTicketsByUserId(@PathVariable int userId) {
        return ticketService.getTicketsByUser(userId);
    }

    @GetMapping("/get/occupiedSeats")
    public ResponseVO getOccupiedSeats(@RequestParam int scheduleId) {
        return ticketService.getBySchedule(scheduleId);
    }

    @PostMapping("/pickUp")
    public ResponseVO pickUpTicket(@RequestParam int ticketId){
        return ticketService.pickUpTicket(ticketId);
    }

    @PostMapping("/refundStrategy/publish")
    public ResponseVO publishRefundStrategy(@RequestBody TicketRefundStrategyForm ticketRefundStrategyForm) {
        return ticketService.addRefundStrategy(ticketRefundStrategyForm);
    }

    @PostMapping("/refundStrategy/update")
    public ResponseVO updateRefundStrategy(@RequestBody TicketRefundStrategyForm ticketRefundStrategyForm) {
        return ticketService.updateRefundStrategy(ticketRefundStrategyForm);
    }

    @PostMapping("/refundStrategy/addMovies")
    public ResponseVO addRefundMovies(@RequestParam int refundStrategyId, @RequestBody List<Integer> movieIdList) {
        return ticketService.addRefundMovies(refundStrategyId, movieIdList);
    }

    @PostMapping("/refundStrategy/removeMovies")
    public ResponseVO removeRefundMovies(@RequestParam int refundStrategyId, @RequestBody List<Integer> movieIdList) {
        return ticketService.removeRefundMovies(refundStrategyId, movieIdList);
    }

    @PostMapping("/refund/choose")
    public ResponseVO chooseRefundTicket(@RequestParam int ticketId) {
        return ticketService.addRefundTicket(ticketId);
    }

    @PostMapping("/refund/confirm")
    public ResponseVO confirmRefundTicket(@RequestParam int ticketId) {
        return ticketService.completeRefundTicket(ticketId);
    }
}
