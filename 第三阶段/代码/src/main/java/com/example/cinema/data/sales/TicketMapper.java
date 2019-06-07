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
     * 插入多个电影票
     */
    int insertTickets(List<Ticket> tickets);

    /**
     * 更新单一电影票状态
     */
    void updateTicketState(
            @Param("ticketId") int ticketId,
            @Param("state") int state
    );

    /**
     * 更新单一电影票实际支付金额
     */
    void updateTicketActualPayment(
            @Param("ticketId") int ticketId,
            @Param("actualPayment") double actualPayment
    );

    /**
     * 选择单一排片场次的所有电影票
     */
    List<Ticket> selectTicketsBySchedule(int scheduleId);

    /**
     * 选择单一排片场次中某座位的单一电影票
     */
    Ticket selectTicketByScheduleIdAndSeat(
            @Param("scheduleId") int scheduleId,
            @Param("column") int columnIndex,
            @Param("row") int rowIndex
    );

    /**
     * 选择单一电影票
     */
    Ticket selectTicketById(int id);

    /**
     * 选择某用户买过的所有电影票（包括所有状态）
     */
    List<Ticket> selectTicketsByUser(int userId);

    /**
     * 选择某订单的电影票
     */
    List<Ticket> selectTicketsByOrder(int orderId);

    /**
     * 定时删除已失效的多个电影票
     */
    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();

    /*================================================================================
    退票策略
     */

    /**
     * 插入单一退票策略
     *
     * @param strategy 没有用到movieList的po
     */
    int insertOneRefundStrategy(TicketRefundStrategy strategy);

    /**
     * 修改单一退票策略
     *
     * @param strategy 没有用到movieList的po
     */
    void updateOneRefundStrategy(TicketRefundStrategy strategy);

    /**
     * 为单一退票策略插入使用该策略的电影列表
     */
    int insertStrategyAndMovies(
            @Param("strategyId") int strategyId,
            @Param("movieIdList") List<Integer> movieIdList
    );

    /**
     * 为单一退票策略删除使用该策略的电影列表
     */
    void deleteStrategyAndMovies(
            @Param("strategyId") int strategyId,
            @Param("movieIdList") List<Integer> movieIdList
    );

    /**
     * 选择单一退票策略
     *
     * @return 用到movieList的po
     */
    TicketRefundStrategy selectRefundStrategyById(@Param("strategyId") int strategyId);

    /**
     * 选择某电影使用的退票策略
     *
     * @return 没有用到movieList的po
     */
    TicketRefundStrategy selectRefundStrategyByMovie(@Param("movieId") int movieId);

    /**
     * 选择所有退票策略
     *
     * @return 用到movieList的po
     */
    List<TicketRefundStrategy> selectRefundStrategies();

    /**
     * 选择所有有效的可供添加到退票策略的中的未下架的电影
     *
     * @return
     */
    List<Movie> selectMovieNotInRefundStrategy();
}
