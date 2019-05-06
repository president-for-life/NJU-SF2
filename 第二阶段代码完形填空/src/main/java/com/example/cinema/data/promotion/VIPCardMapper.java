package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    /**
     * 插入会员卡
     * @param vipCard 会员卡表单
     */
    int insertOneCard(VIPCard vipCard);

    /**
     * 选择某会员卡
     * @param id 会员卡id
     * @return po.VIPCard
     */
    VIPCard selectCardById(int id);

    /**
     * 更新某会员卡的余额
     * @param id 会员卡id
     * @param balance 更新的余额
     */
    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    /**
     * 选择某用户的会员卡
     * @param userId 用户id
     * @return po.VIPCard
     */
    VIPCard selectCardByUserId(int userId);
}
