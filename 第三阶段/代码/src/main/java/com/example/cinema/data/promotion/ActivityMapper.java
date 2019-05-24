package com.example.cinema.data.promotion;

import com.example.cinema.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Mapper
public interface ActivityMapper {

    /**
     * 插入优惠活动
     */
    int insertActivity(Activity activity);

    /**
     * 为某优惠活动插入优惠电影列表
     */
    int insertActivityAndMovie(
            @Param("activityId") int activityId,
            @Param("movieId") List<Integer> movieId
    );

    /**
     * 选择所有优惠活动
     */
    List<Activity> selectActivities();

    /**
     * 选择优惠电影列表中含有某电影的优惠活动
     */
    List<Activity> selectActivitiesByMovie(int movieId);

    /**
     * 选择某优惠活动
     */
    Activity selectById(int id);

    /**
     * 选择条件为“观看任意电影”的优惠活动
     */
    List<Activity> selectActivitiesWithoutMovie();
}
