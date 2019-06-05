package com.example.cinema.bl.sales;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.OrderForm;
import com.example.cinema.vo.TicketRefundStrategyForm;

import java.util.List;

/**
 * @author 李莹
 */
public interface TicketService {

    /**
     * 获得该场次的被锁座位和场次信息
     *
     * @author 李莹
     */
    ResponseVO getBySchedule(int scheduleId);

    /**
     * 锁座，返回总金额、用户拥有的且满足本次订单使用门槛的优惠券
     *
     * @author 梁正川
     */
    ResponseVO addTicket(OrderForm orderForm);

    /**
     * 使用银行卡完成购票
     * 判断支付是否超时，完成购票，删除使用的优惠券，尝试赠送优惠券
     *
     * @author 梁正川
     */
    ResponseVO completeTicket(List<Integer> ticketIdList, int couponId);

    /**
     * 使用会员卡完成购票
     * 判断支付是否超时，会员卡扣款，完成购票，删除使用的优惠券，尝试赠送优惠券
     *
     * @author 梁正川
     */
    ResponseVO completeByVIPCard(List<Integer> ticketIdList, int couponId);

    /**
     * 取票
     *
     * @author 戴羽涵
     */
    ResponseVO pickUpTicket(int ticketId);

    /**
     * 新增退票策略
     *
     * @author 戴羽涵
     * @param strategyForm 没有用到movieList的form
     */
    ResponseVO addRefundStrategy(TicketRefundStrategyForm strategyForm);

    /**
     * 修改退票策略
     *
     * @author 戴羽涵
     * @param strategyForm 没有用到movieList的form
     */
    ResponseVO updateRefundStrategy(TicketRefundStrategyForm strategyForm);

    /**
     * 增加使用某退票策略的电影
     *
     * @author 戴羽涵
     */
    ResponseVO addRefundMovies(int refundStrategyId, List<Integer> movieIdList);

    /**
     * 减少使用某退票策略的电影
     *
     * @author 戴羽涵
     */
    ResponseVO removeRefundMovies(int refundStrategyId, List<Integer> movieIdList);

    /**
     * 用户请求退票
     *
     * 根据退票策略，检查是否符合退票条件
     * 若符合，返回这张票使用的退票策略
     *
     * @author 戴羽涵
     */
    ResponseVO addRefundTicket(int ticketId);

    /**
     * 完成退票
     *
     * @author 戴羽涵
     */
    ResponseVO completeRefundTicket(int ticketId);

    /**
     * 获得用户买过的所有电影票（包括所有状态）
     *
     * @author 戴羽涵
     */
    ResponseVO getTicketsByUser(int userId);

    /**
     * 获得用户的所有订单（包括所有状态）
     *
     * @author 戴羽涵
     */
    ResponseVO getOrdersByUser(int userId);
}
