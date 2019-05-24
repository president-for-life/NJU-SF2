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
     * @param vipCardStrategy 会员卡策略
     */
    int insertStrategy(VIPCardStrategy vipCardStrategy);

    /**
     * 更新会员卡策略
     *
     * @author 梁正川
     * @param vipCardStrategy 会员卡策略
     */
    void updateStrategy(VIPCardStrategy vipCardStrategy);

    /**
     * 选择某会员卡策略
     *
     * @author 梁正川
     */
    VIPCardStrategy selectStrategyById(int id);

    /*================================================================================
    会员卡
     */

    /**
     * 插入会员卡
     *
     * @param vipCard 会员卡po
     */
    int insertOneCard(VIPCard vipCard);

    /**
     * 选择某会员卡
     *
     * @param id 会员卡id
     * @return po.VIPCard
     */
    VIPCard selectCardById(int id);

    /**
     * 更新某会员卡的余额
     *
     * @param id      会员卡id
     * @param balance 更新的余额
     */
    void updateCardBalance(@Param("id") int id, @Param("balance") double balance);

    /**
     * 选择某用户的会员卡
     */
    VIPCard selectCardByUserId(int userId);

    /*================================================================================
    会员卡充值
     */

    /***
     * 选择某用户的充值记录
     */
    List<VIPCardCharge> selectChargesByUser(@Param("userId") int userId);
}
