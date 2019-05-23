package com.example.cinema.blImpl.sales;

import com.example.cinema.po.Ticket;

import java.util.List;

public interface TicketServiceForBl {
    /**
     * 获得某用户的电影票
     *
     * @author 梁正川
     */
    List<Ticket> getTicketsByUserForBl(int userId);
}
