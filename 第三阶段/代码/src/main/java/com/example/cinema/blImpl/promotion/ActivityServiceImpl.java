package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.po.Activity;
import com.example.cinema.po.Coupon;
import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/20
 */
@Service
public class ActivityServiceImpl implements ActivityService, ActivityServiceForBl {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    CouponService couponService;

    @Override
    @Transactional
    public ResponseVO publishActivity(ActivityForm activityForm) {
        try {
            // 创建优惠券
            ResponseVO vo = couponService.addCoupon(activityForm.getCouponForm());
            Coupon coupon = (Coupon) vo.getContent();

            // 创建优惠活动
            Activity activity = new Activity();
            activity.setName(activityForm.getName());
            activity.setDescription(activityForm.getName());
            activity.setStartTime(activityForm.getStartTime());
            activity.setEndTime(activityForm.getEndTime());
            activity.setCoupon(coupon);

            // 插入优惠活动 TODO activity中的id会自动生成吗？
            activityMapper.insertActivity(activity);

            // 优惠活动的条件为“购买指定电影”
            if (activityForm.getMovieList() != null
                    && activityForm.getMovieList().size() != 0) {
                activityMapper.insertActivityAndMovie(
                        activity.getId(), activityForm.getMovieList()
                );
            }

            return ResponseVO.buildSuccess(activityMapper.selectById(activity.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getActivities() {
        try {
            return ResponseVO.buildSuccess(activityMapper.selectActivities().stream().map(Activity::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<Activity> getActivitiesForBl() {
        try {
            return activityMapper.selectActivities();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
