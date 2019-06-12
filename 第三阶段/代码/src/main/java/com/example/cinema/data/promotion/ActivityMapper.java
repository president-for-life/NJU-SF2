package com.example.cinema.data.promotion;

import com.example.cinema.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/20
 */
@Mapper
public interface ActivityMapper {

    /**
     * @author 李莹
     */
    void insertActivity(Activity activity);

    /**
     * 为某优惠活动插入优惠电影列表
     *
     * @author 李莹
     */
    void insertActivityAndMovie(
            @Param("activityId") int activityId,
            @Param("movieId") List<Integer> movieId
    );

    /**
     * @author 李莹
     */
    List<Activity> selectActivities();

    /**
     * 选择优惠电影列表中含有某电影的优惠活动
     *
     * @author 李莹
     */
    List<Activity> selectActivitiesByMovie(int movieId);

    /**
     * 选择某优惠活动
     *
     * @author 李莹
     */
    Activity selectById(int id);

    /**
     * 选择条件为“观看任意电影”的优惠活动
     *
     * @author 李莹
     */
    List<Activity> selectActivitiesWithoutMovie();
}
