package com.example.cinema.data.promotion;

import com.example.cinema.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/17
 */
@Mapper
public interface CouponMapper {

    /**
     * @author 李莹
     */
    int insertCoupon(Coupon coupon);

    /**
     * 获得当前处于有效期的优惠劵
     *
     * @author 梁正川
     */
    List<Coupon> selectValidCoupons();

    /**
     * @author 李莹
     */
    List<Coupon> selectCouponByUser(int userId);

    /**
     * @author 李莹
     */
    Coupon selectById(int id);

    /**
     * 让某观众获得某优惠劵
     *
     * @author 李莹
     */
    void insertCouponUser(@Param("couponId") int couponId, @Param("userId") int userId);

    /**
     * 删除某观众拥有的优惠劵
     *
     * @author 李莹
     */
    void deleteCouponUser(@Param("couponId") int couponId, @Param("userId") int userId);

    /**
     * 获得某用户拥有的、使用门槛低于amount的优惠劵
     *
     * @author 李莹
     */
    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId, @Param("amount") double amount);
}
