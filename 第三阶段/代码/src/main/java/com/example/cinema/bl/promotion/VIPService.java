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
     * @author 梁正川
     */
    ResponseVO addStrategy(VIPCardStrategyForm strategyForm);

    /**
     * @author 梁正川
     */
    ResponseVO updateStrategy(VIPCardStrategyForm strategyForm);

    /**
     * @author 梁正川
     */
    ResponseVO getStrategy(int strategyId);

    /**
     * @author 梁正川
     */
    ResponseVO removeStrategy(int strategyId);

    /**
     * @author 梁正川
     */
    ResponseVO getAllStrategies();

    /*================================================================================
    会员卡
     */

    /**
     * 某用户获得使用某种策略的会员卡
     * 如果用户已持有会员卡，切换会员卡使用的策略
     *
     * @author 梁正川
     */
    ResponseVO addVIPCard(int userId, int strategyId);

    /**
     * @author 李莹
     */
    ResponseVO charge(VIPCardChargeForm vipCardChargeForm);

    /**
     * @author 李莹
     */
    ResponseVO getCardByUserId(int userId);

    /**
     * @author 梁正川
     */
    ResponseVO getChargeRecords(int vipCardId);
}
