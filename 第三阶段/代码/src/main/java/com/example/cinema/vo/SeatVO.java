package com.example.cinema.vo;

import java.sql.Timestamp;

public class SeatVO {

    /**
     * 电影票id
     */
    private int id;

    /**
     * 列号
     */
    private int columnIndex;

    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 电影票状态：
     * 0：支付未完成
     * 1：支付已完成但未出票
     * 2：已失效
     * 3：已出票
     * 4：退票未完成
     * 5：退票已完成
     */
    private String state;

    /**
     * 实际支付金额
     */
    private double actualPayment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(double actualPayment) {
        this.actualPayment = actualPayment;
    }
}
