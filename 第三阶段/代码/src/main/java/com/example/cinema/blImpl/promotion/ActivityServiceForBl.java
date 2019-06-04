package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Activity;

import java.util.List;

/**
 * @author 梁正川
 */
public interface ActivityServiceForBl {

    /**
     * 查找所有正在进行的优惠活动
     *
     * @author 梁正川
     */
    List<Activity> getOngoingActivities();

}
