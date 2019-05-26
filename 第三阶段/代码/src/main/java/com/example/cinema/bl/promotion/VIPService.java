package com.example.cinema.bl.promotion;

import com.example.cinema.po.VIPCardCharge;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.VIPCardStrategyForm;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/14
 */
public interface VIPService {

    /*================================================================================
    会员卡策略
     */

    /**
     * 发布会员卡充值优惠策略
     *
     * @author 梁正川
     * @param strategyForm 发布会员卡表单
     * @return vo.ResponseVO
     */
    ResponseVO addStrategy(VIPCardStrategyForm strategyForm);

    /**
     * 修改会员卡充值优惠策略
     *
     * @author 梁正川
     * @param strategyForm 修改会员卡表单
     * @return vo.ResponseVO
     */
    ResponseVO updateStrategy(VIPCardStrategyForm strategyForm);

    /**
     * 获得会员卡充值优惠策略
     *
     * @author 梁正川
     */
    ResponseVO getStrategy(int strategyId);

    /*================================================================================
    会员卡
     */

    /**
     * 某用户获得使用某种策略的会员卡
     * 如果用户已持有会员卡，切换会员卡使用的策略
     */
    ResponseVO addVIPCard(int userId, int strategyId);

    /**
     * 根据id获得某会员卡
     */
    ResponseVO getCardById(int id);

    /**
     * 获得会员卡信息
     */
    ResponseVO getVIPCardStrategy(int strategyId);

    /**
     * 充值会员卡
     */
    ResponseVO charge(VIPCardChargeForm vipCardChargeForm);

    /**
     * 获得某用户的会员卡
     */
    ResponseVO getCardByUserId(int userId);

    /**
     * 查找某用户会员卡充值记录
     *
     * @author 梁正川
     */
    ResponseVO getChargeRecords(int userId);
}
