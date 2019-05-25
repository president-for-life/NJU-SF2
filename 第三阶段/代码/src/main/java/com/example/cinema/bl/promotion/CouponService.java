package com.example.cinema.bl.promotion;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author 李莹
 * @date 2019/4/17
 */
public interface CouponService {

    /**
     * 获得某用户拥有的优惠劵
     *
     * @param userId 用户id
     * @return vo.ResponseVO
     */
    ResponseVO getCouponsByUser(int userId);

    /**
     * 发布优惠券
     *
     * @param couponForm 优惠券表单
     * @return vo.ResponseVO
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 让某观众获得某优惠劵
     *
     * @param couponId 优惠券id
     * @param userId   用户id
     * @return vo.ResponseVO
     */
    ResponseVO issueCoupon(int couponId, int userId);

}
