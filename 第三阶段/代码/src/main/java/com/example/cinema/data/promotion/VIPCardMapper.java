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
     * @author 梁正川
     */
    int insertStrategy(VIPCardStrategy vipCardStrategy);

    /**
     * @author 梁正川
     */
    void updateStrategy(VIPCardStrategy vipCardStrategy);

    /**
     * @author 梁正川
     */
    void deleteStrategy(@Param("id") int id);

    /**
     * @author 梁正川
     */
    VIPCardStrategy selectStrategyById(@Param("id") int id);

    /**
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
     * @author 梁正川
     */
    void insertOneCard(VIPCard vipCard);

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
     * @author 李莹
     */
    void updateCardBalance(
            @Param("id") int id,
            @Param("balance") double balance
    );

    /**
     * @author 梁正川
     */
    VIPCard selectCardById(@Param("id") int id);

    /**
     * @author 梁正川
     */
    VIPCard selectCardByUserId(@Param("userId") int userId);

    /*================================================================================
    会员卡充值记录
     */

    /**
     * @author 梁正川
     */
    int insertOneChargeRecord(VIPCardCharge charge);

    /***
     * @author 梁正川
     */
    List<VIPCardCharge> selectChargeRecordsByCard(@Param("vipCardId") int vipCardId);
}
