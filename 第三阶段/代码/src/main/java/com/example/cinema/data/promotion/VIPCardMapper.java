package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCardCharge;
import com.example.cinema.po.VIPCardStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/14
 */
@Mapper
public interface VIPCardMapper {

    /*================================================================================
    会员卡策略
     */

    /**
     * 插入会员卡策略
     *
     * @author 梁正川
     */
    int insertStrategy(VIPCardStrategy vipCardStrategy);

    /**
     * 更新会员卡策略
     *
     * @author 梁正川
     */
    void updateStrategy(VIPCardStrategy vipCardStrategy);

    /**
     * 选择单一会员卡策略
     *
     * @author 梁正川
     */
    VIPCardStrategy selectStrategyById(@Param("id") int id);

    /**
     * 选择所有会员卡策略
     *
     * @author 梁正川
     */
    List<VIPCardStrategy> selectStrategies();

    /**
     * 使用某会员卡策略的会员卡数量
     *
     * @author 梁正川
     */
    int strategyUseCount(@Param("id") int id);

    /*================================================================================
    会员卡
     */

    /**
     * 插入单一会员卡
     *
     * @author 梁正川
     */
    int insertOneCard(VIPCard vipCard);

    /**
     * 切换会员卡使用的策略
     *
     * @author 梁正川
     */
    void updateCardStrategy(
            @Param("id") int id,
            @Param("strategyId") int strategyId
    );

    /**
     * 更新单一会员卡的余额
     *
     * @param balance 更新的余额
     */
    void updateCardBalance(
            @Param("id") int id,
            @Param("balance") double balance
    );

    /**
     * 选择单一会员卡
     *
     * @author 梁正川
     */
    VIPCard selectCardById(@Param("id") int id);

    /**
     * 根据用户选择单一会员卡
     */
    VIPCard selectCardByUserId(@Param("userId") int userId);

    /*================================================================================
    会员卡充值记录
     */

    /**
     * 插入单一充值记录
     *
     * @author 梁正川
     */
    int insertOneChargeRecord(VIPCardCharge charge);

    /***
     * 选择某用户的充值记录
     *
     * @author 梁正川
     */
    List<VIPCardCharge> selectChargeRecordsByCard(@Param("vipCardId") int vipCardId);
}
