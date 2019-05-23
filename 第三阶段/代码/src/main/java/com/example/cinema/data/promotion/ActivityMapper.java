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
     *
     * @param activity 优惠活动表单
     */
    int insertActivity(Activity activity);

    /**
     * 为某优惠活动插入优惠电影列表
     *
     * @param activityId 优惠活动id
     * @param movieId    电影id数组
     */
    int insertActivityAndMovie(@Param("activityId") int activityId, @Param("movieId") List<Integer> movieId);

    /**
     * 选择所有优惠活动
     *
     * @return po.Activity数组
     */
    List<Activity> selectActivities();

    /**
     * 选择优惠电影列表中含有某电影的优惠活动
     *
     * @return po.Activity数组
     */
    List<Activity> selectActivitiesByMovie(int movieId);

    /**
     * 选择某优惠活动
     *
     * @return po.Activity
     */
    Activity selectById(int id);

    /**
     * ？
     *
     * @return po.Activity数组
     */
    List<Activity> selectActivitiesWithoutMovie();
}
