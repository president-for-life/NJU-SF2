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
     * 获得某用户拥有的优惠劵
     *
     * @param userId 用户id
     * @return po.Coupon数组
     * @author 梁正川
     */
    List<Coupon> getCouponsByUserForBl(int userId);

    /**
     * 发布优惠券
     *
     * @param couponForm 优惠券表单
     * @return vo.ResponseVO
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 删除某用户拥有的某优惠券
     *
     * @param couponId 优惠券id
     * @param userId   用户id
     * @author 梁正川
     */
    void deleteCouponUser(int couponId, int userId);

    /**
     * 根据id查找优惠券
     *
     * @param id 优惠券id
     * @return po.Coupon
     * @author 梁正川
     */
    Coupon getCouponById(int id);

    /**
     * 让多个观众获得某优惠劵
     *
     * @author 梁正川
     */
    ResponseVO issueCoupon(int couponId, List<Integer> userIdList);
}
