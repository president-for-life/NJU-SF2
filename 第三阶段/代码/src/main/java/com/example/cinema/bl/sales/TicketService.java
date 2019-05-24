package com.example.cinema.bl.sales;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.TicketRefundStrategyForm;

import java.util.List;

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
    ResponseVO addTicket(TicketForm ticketForm);

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
     * 新增退票策略 TODO
     *
     * @author 戴羽涵
     */
    ResponseVO addRefundStrategy(TicketRefundStrategyForm strategyForm);

    /**
     * 请求退票 TODO
     * 返回退票相关信息
     *
     * @author 戴羽涵
     */
    ResponseVO addRefundTicket(int ticketId);

    /**
     * 完成退票 TODO
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
}
