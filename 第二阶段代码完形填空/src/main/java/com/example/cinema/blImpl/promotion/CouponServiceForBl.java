package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Coupon;

public interface CouponServiceForBl {
    /**
     * 根据id查找优惠券
     * @author 梁正川
     * @param id 优惠券id
     * @return po.Coupon
     */
    Coupon getCouponById(int id);
}
