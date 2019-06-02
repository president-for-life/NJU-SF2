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
     * TESTED
     *
     * @author 梁正川
     */
    ResponseVO addStrategy(VIPCardStrategyForm strategyForm);

    /**
     * 修改会员卡充值优惠策略
     * TESTED
     *
     * @author 梁正川
     */
    ResponseVO updateStrategy(VIPCardStrategyForm strategyForm);

    /**
     * 获得会员卡充值优惠策略
     * TESTED
     *
     * @author 梁正川
     */
    ResponseVO getStrategy(int strategyId);

    /**
     * 删除会员卡充值优惠策略
     *
     * @author 梁正川
     */
    ResponseVO removeStrategy(int strategyId);

    /**
     * 获得所有会员卡充值优惠策略
     * TESTED
     *
     * @author 梁正川
     */
    ResponseVO getAllStrategies();

    /*================================================================================
    会员卡
     */

    /**
     * 某用户获得使用某种策略的会员卡
     * 如果用户已持有会员卡，切换会员卡使用的策略
     * TESTED
     */
    ResponseVO addVIPCard(int userId, int strategyId);

    /**
     * 获得会员卡信息
     * TESTED
     */
    ResponseVO getVIPCardStrategy(int strategyId);

    /**
     * 充值会员卡
     * TESTED
     */
    ResponseVO charge(VIPCardChargeForm vipCardChargeForm);

    /**
     * 获得某用户的会员卡
     * TESTED
     */
    ResponseVO getCardByUserId(int userId);

    /**
     * 查找某用户会员卡充值记录
     * TESTED
     *
     * @author 梁正川
     */
    ResponseVO getChargeRecords(int vipCardId);
}
