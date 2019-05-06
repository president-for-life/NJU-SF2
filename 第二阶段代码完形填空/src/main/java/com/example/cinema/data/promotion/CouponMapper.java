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
     * @param coupon 优惠券表单
     */
    int insertCoupon(Coupon coupon);

    /**
     * 选择某用户拥有的优惠劵
     * @param userId 用户id
     * @return po.Coupon数组
     */
    List<Coupon> selectCouponByUser(int userId);

    /**
     * 选择某优惠劵
     * @param id 优惠券id
     * @return po.Coupon
     */
    Coupon selectById(int id);

    /**
     * 让某观众获得某优惠劵
     * @param couponId 优惠券id
     * @param userId 用户id
     */
    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    /**
     * 删除某观众拥有的优惠劵
     * @param couponId 优惠券id
     * @param userId 用户id
     */
    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    /**
     * ？
     * @param userId 用户id
     * @param amount ？
     * @return po.Coupon数组
     */
    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);
}
