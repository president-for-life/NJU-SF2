package com.example.cinema.bl.sales;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;

import java.util.List;


/**
 * Created by liying on 2019/4/16.
 */
public interface TicketService {
    /**
     * TODO:锁座【增加票但状态为未付款】
     * 梁正川这样实现该方法：锁座，计算总金额，计算用户拥有的且满足本次订单使用门槛的优惠券
     *
     * @param ticketForm 购票表单
     * @return vo.ResponseVO，包含TicketWithCouponVO
     * @author 梁正川
     */
    ResponseVO addTicket(TicketForm ticketForm);

    /**
     * TODO:完成购票【不使用会员卡】流程包括校验优惠券和根据优惠活动赠送优惠券
     * 梁正川这样实现该方法：判断支付是否超时，删除使用的优惠券，尝试赠送优惠券
     *
     * @param id       电影票id数组
     * @param couponId 优惠券id
     * @return vo.ResponseVO
     * @author 梁正川
     */
    ResponseVO completeTicket(List<Integer> id, int couponId);

    /**
     * 获得该场次的被锁座位和场次信息
     *
     * @param scheduleId 排片id
     * @return vo.ResponseVO
     */
    ResponseVO getBySchedule(int scheduleId);

    /**
     * TODO:获得用户买过的票
     *
     * @param userId 用户id
     * @return vo.ResponseVO，包含vo.TicketWithScheduleVO
     * @author 戴羽涵
     */
    ResponseVO getTicketByUser(int userId);

    /**
     * TODO:完成购票【使用会员卡】流程包括会员卡扣费、校验优惠券和根据优惠活动赠送优惠券
     * 梁正川这样实现该方法：判断支付是否超时，会员卡扣款，删除使用的优惠券，尝试赠送优惠券
     *
     * @param id       电影票id数组
     * @param couponId 优惠券id
     * @return vo.ResponseVO
     * @author 梁正川
     */
    ResponseVO completeByVIPCard(List<Integer> id, int couponId);

    /**
     * TODO:取消锁座（只有状态是"锁定中"的可以取消）
     *
     * @param id 电影票id数组
     * @return vo.ResponseVO
     * @author 戴羽涵
     */
    ResponseVO cancelTicket(List<Integer> id);
}
