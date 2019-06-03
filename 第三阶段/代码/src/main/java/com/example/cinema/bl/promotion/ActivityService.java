package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author 李莹
 * @date 2019/4/20
 */
public interface ActivityService {

    /**
     * 发布优惠活动
     *
     * @author 李莹
     */
    ResponseVO publishActivity(ActivityForm activityForm);

    /**
     * 获得所有优惠活动
     *
     * @author 李莹
     */
    ResponseVO getActivities();

}
