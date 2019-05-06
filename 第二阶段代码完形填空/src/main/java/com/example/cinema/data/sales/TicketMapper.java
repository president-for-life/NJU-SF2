package com.example.cinema.data.sales;

import com.example.cinema.po.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper
public interface TicketMapper {

    /**
     * 插入单一电影票
     */
    int insertTicket(Ticket ticket);

    /**
     * 插入多个电影票
     */
    int insertTickets(List<Ticket> tickets);

    /**
     * 删除单一电影票
     */
    void deleteTicket(int ticketId);

    /**
     * 更新电影票状态
     */
    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    /**
     *
     */
    List<Ticket> selectTicketsBySchedule(int scheduleId);

    /**
     *
     */
    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    /**
     * 选择某电影票
     */
    Ticket selectTicketById(int id);

    /**
     * 选择某用户买过的电影票
     */
    List<Ticket> selectTicketByUser(int userId);

    /**
     * 删除失效的电影票
     */
    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();
}

