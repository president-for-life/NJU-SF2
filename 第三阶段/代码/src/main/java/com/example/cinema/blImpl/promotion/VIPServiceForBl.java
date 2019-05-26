package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.VIPCardCharge;

import java.util.List;

/**
 * @author 梁正川
 */
public interface VIPServiceForBl {

    /**
     * 使用会员卡付款
     *
     * @author 梁正川
     */
    boolean pay(int userId, double pay);
}
