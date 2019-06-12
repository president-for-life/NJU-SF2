package com.example.cinema.data.sales;

import com.example.cinema.po.Movie;
import com.example.cinema.po.Ticket;
import com.example.cinema.po.TicketRefundStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/16
 */
@Mapper
public interface TicketMapper {

    /**
     * @author 李莹
     */
    int insertTickets(List<Ticket> tickets);

    /**
     * @author 李莹
     */
    void updateTicketState(
            @Param("ticketId") int ticketId,
            @Param("state") int state
    );

    /**
     * @author 梁正川
     */
    void updateTicketActualPayment(
            @Param("ticketId") int ticketId,
            @Param("actualPayment") double actualPayment
    );

    /**
     * @author 李莹
     */
    List<Ticket> selectTicketsBySchedule(int scheduleId);

    /**
     * @author 李莹
     */
    Ticket selectTicketByScheduleIdAndSeat(
            @Param("scheduleId") int scheduleId,
            @Param("column") int columnIndex,
            @Param("row") int rowIndex
    );

    /**
     * @author 李莹
     */
    Ticket selectTicketById(int id);

    /**
     * @author 李莹
     */
    List<Ticket> selectTicketsByUser(int userId);

    /**
     * @author 梁正川
     */
    List<Ticket> selectTicketsByOrder(int orderId);

    /**
     * 定时删除已失效的多个电影票
     *
     * @author 李莹
     */
    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();

    /*================================================================================
    退票策略
     */

    /**
     * @author 戴羽涵
     * @param strategy 没有用到movieList的po
     */
    int insertOneRefundStrategy(TicketRefundStrategy strategy);

    /**
     * @author 戴羽涵
     * @param strategy 没有用到movieList的po
     */
    void updateOneRefundStrategy(TicketRefundStrategy strategy);

    /**
     * 为单一退票策略插入使用该策略的电影列表
     *
     * @author 戴羽涵
     */
    int insertStrategyAndMovies(
            @Param("strategyId") int strategyId,
            @Param("movieIdList") List<Integer> movieIdList
    );

    /**
     * 为单一退票策略删除使用该策略的电影列表
     *
     * @author 戴羽涵
     */
    void deleteStrategyAndMovies(
            @Param("strategyId") int strategyId,
            @Param("movieIdList") List<Integer> movieIdList
    );

    /**
     * @author 戴羽涵
     * @return 用到movieList的po
     */
    TicketRefundStrategy selectRefundStrategyById(@Param("strategyId") int strategyId);

    /**
     * @author 戴羽涵
     * @return 没有用到movieList的po
     */
    TicketRefundStrategy selectRefundStrategyByMovie(@Param("movieId") int movieId);

    /**
     * @author 戴羽涵
     * @return 用到movieList的po
     */
    List<TicketRefundStrategy> selectRefundStrategies();

    /**
     * 选择所有有效的可供添加到退票策略的中的未下架的电影
     *
     * @author 戴羽涵
     */
    List<Movie> selectMovieNotInRefundStrategy();
}
