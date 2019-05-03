package com.example.cinema.data.promotion;

import com.example.cinema.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Mapper
public interface CouponMapper {

    /**
     * 插入优惠券
     */
    int insertCoupon(Coupon coupon);

    /**
     * 选择某用户拥有的优惠劵
     */
    List<Coupon> selectCouponByUser(int userId);

    /**
     * 选择某优惠劵
     */
    Coupon selectById(int id);

    /**
     * 让某观众获得某优惠劵
     */
    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    /**
     * 删除某观众拥有的优惠劵
     */
    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    /**
     * ？
     */
    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);
}
