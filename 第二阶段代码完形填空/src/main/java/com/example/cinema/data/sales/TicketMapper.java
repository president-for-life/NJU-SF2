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
     * @param ticket po.Ticket
     */
    int insertTicket(Ticket ticket);

    /**
     * 插入多个电影票
     * @param tickets po.Ticket数组
     */
    int insertTickets(List<Ticket> tickets);

    /**
     * 删除单一电影票
     * @param ticketId 电影票id
     */
    void deleteTicket(int ticketId);

    /**
     * 更新电影票状态
     * @param ticketId 电影票id
     * @param state 状态
     */
    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    /**
     * 选择某排片场次的电影票
     * @param scheduleId 排片id
     * @return po.Ticket数组
     */
    List<Ticket> selectTicketsBySchedule(int scheduleId);

    /**
     * 选择某排片场次中某座位的电影票
     * @param scheduleId 排片id
     * @param columnIndex 列号
     * @param rowIndex 行号
     * @return po.Ticket
     */
    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    /**
     * 选择某电影票
     * @param id 电影票id
     * @return po.Ticket
     */
    Ticket selectTicketById(int id);

    /**
     * 选择某用户买过的电影票
     * @param userId 用户id
     * @return po.Ticket数组
     */
    List<Ticket> selectTicketByUser(int userId);

    /**
     * 删除失效的电影票
     */
    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();
}

