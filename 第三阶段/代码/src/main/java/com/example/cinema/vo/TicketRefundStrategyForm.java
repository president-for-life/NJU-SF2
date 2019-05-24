package com.example.cinema.vo;

import com.example.cinema.po.Movie;
import com.example.cinema.po.TicketRefundStrategy;

import java.util.List;

public class TicketRefundStrategyForm {

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

    /**
     * 使用本退票策略的电影列表
     */
    private List<Movie> movieList;

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
        if(ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("返还比例必须在0到1之间（包含）！");
        }
        this.ratio = ratio;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public TicketRefundStrategy getPO() {
        TicketRefundStrategy ticketRefundStrategy = new TicketRefundStrategy();
        ticketRefundStrategy.setId(this.getId());
        ticketRefundStrategy.setRefundable(this.getRefundable());
        ticketRefundStrategy.setRatio(this.getRatio());
        ticketRefundStrategy.setTime(this.getTime());
        ticketRefundStrategy.setMovieList(this.getMovieList());
        return ticketRefundStrategy;
    }
}
