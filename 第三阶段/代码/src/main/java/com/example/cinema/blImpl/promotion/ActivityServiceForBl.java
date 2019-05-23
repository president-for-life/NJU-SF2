package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Activity;

import java.util.List;

public interface ActivityServiceForBl {

    /**
     * 查找所有优惠活动
     *
     * @return po.Activity数组
     * @author 梁正川
     */
    List<Activity> getActivitiesForBl();
}
