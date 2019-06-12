package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Coupon;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;

import java.util.List;

/**
 * @author 梁正川
 */
public interface CouponServiceForBl {

    /**
     * 获得某用户拥有的、使用门槛低于amount的优惠劵
     *
     * @author 梁正川
     */
    List<Coupon> getCouponsByUserAndAmount(int userId, double amount);

    /**
     * @author 梁正川
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 删除某用户拥有的某优惠券
     *
     * @author 梁正川
     */
    void deleteCouponUser(int couponId, int userId);

    /**
     * @author 梁正川
     */
    Coupon getCouponById(int couponId);

    /**
     * 让多个观众每人都获得多个优惠劵
     *
     * @author 梁正川
     */
    ResponseVO issueCoupon(List<Integer> couponIdList, List<Integer> userIdList);
}
