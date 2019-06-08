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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李莹
 * @date 2019/4/20
 */
@Service
public class ActivityServiceImpl implements ActivityService, ActivityServiceForBl {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    CouponServiceForBl couponService;

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

            // 插入优惠活动
            activityMapper.insertActivity(activity);

            // 优惠活动的条件为“购买指定电影”
            if (activityForm.getMovieList() != null
                    && activityForm.getMovieList().size() != 0) {
                activityMapper.insertActivityAndMovie(
                        activity.getId(),
                        activityForm.getMovieList()
                );
            }

            return ResponseVO.buildSuccess(
                    activityMapper.selectById(activity.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getActivities() {
        try {
            return ResponseVO.buildSuccess(
                    activityMapper.selectActivities()
                            .stream()
                            .map(Activity::getVO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<Activity> getActivitiesByMovie(int movieId) {
        try {
            return activityMapper.selectActivitiesByMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Activity> getActivitiesWithoutMovie() {
        try {
            return activityMapper.selectActivitiesWithoutMovie();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void participate(int movieId, int userId) {
        try {
            List<Activity> activitiesWithMovie
                    = this.getActivitiesByMovie(movieId);
            List<Activity> activitiesWithoutMovie
                    = this.getActivitiesWithoutMovie();
            activitiesWithMovie.addAll(activitiesWithoutMovie);

            List<Coupon> couponsToBeIssued = new ArrayList<>(); // 赠送的优惠券
            for(Activity activity : activitiesWithMovie) {
                couponsToBeIssued.add(activity.getCoupon());
            }

            // 赠送优惠券
            for (Coupon coupon : couponsToBeIssued) {
                couponService.issueCoupon(Arrays.asList(coupon.getId()), Arrays.asList(userId));
            }

            ////////////////////控制台测试信息////////////////////
            System.out.print("赠送优惠券：");
            for (Coupon coupon : couponsToBeIssued) {
                System.out.print(" 满" + coupon.getTargetAmount() + "送" + coupon.getDiscountAmount());
            }
            System.out.println();
            ////////////////////控制台测试信息////////////////////
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
