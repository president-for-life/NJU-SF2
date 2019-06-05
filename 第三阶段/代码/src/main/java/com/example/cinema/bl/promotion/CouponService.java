package com.example.cinema.bl.promotion;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/17
 */
public interface CouponService {

    /**
     * 获得当前处于有效期的优惠劵
     *
     * @author 梁正川
     */
    ResponseVO getValidCoupons();

    /**
     * 获得某用户拥有的优惠劵
     *
     * @author 李莹
     */
    ResponseVO getCouponsByUser(int userId);

    /**
     * 发布优惠券
     *
     * @author 李莹
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 让多个观众获得多个优惠劵
     *
     * @author 梁正川
     */
    ResponseVO issueCoupon(List<Integer> couponIdList, List<Integer> userIdList);

}
