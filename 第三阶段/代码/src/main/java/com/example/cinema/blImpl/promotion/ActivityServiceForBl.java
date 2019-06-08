package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Activity;

import java.util.List;

/**
 * @author 梁正川
 */
public interface ActivityServiceForBl {

    /**
     * 某用户观看某电影，尝试参与优惠活动
     *
     * @author 梁正川
     */
    void participate(int movieId, int userId);

}
