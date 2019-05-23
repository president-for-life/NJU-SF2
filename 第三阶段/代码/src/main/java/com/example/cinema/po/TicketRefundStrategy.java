package com.example.cinema.po;

public class TicketRefundStrategy {
    /**
     * 退票策略id
     */
    private int id;

    /**
     * 是否可退票
     */
    private int refundable;

    /**
     * 返还比例
     */
    private double ratio;

    /**
     * 开场前 time 分钟不许退票
     */
    private int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getRefundable() {
        return refundable == 1;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable ? 1 : 0;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
