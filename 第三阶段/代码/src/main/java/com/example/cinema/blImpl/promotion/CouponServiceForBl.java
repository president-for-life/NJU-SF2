package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Coupon;
import com.example.cinema.vo.ResponseVO;

import java.util.List;

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
     * 让某观众获得某优惠劵
     *
     * @param couponId 优惠券id
     * @param userId   用户id
     * @return vo.ResponseVO
     */
    ResponseVO issueCoupon(int couponId, int userId);
}
