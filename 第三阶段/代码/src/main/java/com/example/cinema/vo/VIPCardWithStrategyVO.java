package com.example.cinema.vo;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCardStrategy;

import java.sql.Timestamp;

/**
 * @author 梁正川
 * @date 2019/05/29
 */
public class VIPCardWithStrategyVO {

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡策略
     */
    private VIPCardStrategy strategy;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;

    public VIPCardWithStrategyVO() {

    }

    public VIPCardWithStrategyVO(VIPCard card, VIPCardStrategy strategy) {
        this.setId(card.getId());
        this.setStrategy(strategy);
        this.setUserId(card.getUserId());
        this.setBalance(card.getBalance());
        this.setJoinDate(card.getJoinDate());
    }

    public int getId() {
        return id;
    }

    public VIPCardStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(VIPCardStrategy strategy) {
        this.strategy = strategy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }
}
