package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Activity;

import java.util.List;

/**
 * @author 梁正川
 */
public interface ActivityServiceForBl {

    /**
     * 查找优惠电影列表中含有某电影的优惠活动
     *
     * @author 梁正川
     */
    List<Activity> getActivitiesByMovie(int movieId);

    /**
     * 查找条件为“观看任意电影”的优惠活动
     *
     * @author 梁正川
     */
    List<Activity> getActivitiesWithoutMovie();

    /**
     * 查找所有正在进行的优惠活动
     *
     * @author 梁正川
     */
    List<Activity> getOngoingActivities();

}
