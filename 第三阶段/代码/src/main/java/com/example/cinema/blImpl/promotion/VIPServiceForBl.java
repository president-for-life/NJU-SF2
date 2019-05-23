package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.VIPCardCharge;

import java.util.List;

public interface VIPServiceForBl {

    /**
     * 使用会员卡付款
     *
     * @author 梁正川
     */
    boolean pay(int userId, double pay);

    /**
     * 查找某用户会员卡充值记录
     *
     * @author 梁正川
     */
    List<VIPCardCharge> getChargeRecords(int userId);
}
