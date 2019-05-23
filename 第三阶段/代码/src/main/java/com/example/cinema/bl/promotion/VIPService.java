package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.VIPCardStrategyForm;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {
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

    /**
     * 某用户获得会员卡
     *
     * @param userId 用户id
     * @return vo.ResponseVO
     */
    ResponseVO addVIPCard(int userId);

    /**
     * 根据id获得某会员卡
     *
     * @param id 会员卡id
     * @return vo.ResponseVO
     */
    ResponseVO getCardById(int id);

    /**
     * 获得会员卡信息
     *
     * @return vo.ResponseVO
     */
    ResponseVO getVIPInfo();

    /**
     * 充值会员卡
     *
     * @param vipCardChargeForm 会员卡表单
     * @return vo.ResponseVO
     */
    ResponseVO charge(VIPCardChargeForm vipCardChargeForm);

    /**
     * 获得某用户的会员卡
     *
     * @param userId 用户id
     * @return vo.ResponseVO
     */
    ResponseVO getCardByUserId(int userId);

}
