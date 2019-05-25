package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
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

}
