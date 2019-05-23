package com.example.cinema.blImpl.promotion;

public interface VIPServiceForBl {

    /**
     * 使用会员卡付款
     *
     * @author 梁正川
     */
    boolean pay(int userId, double pay);
}
