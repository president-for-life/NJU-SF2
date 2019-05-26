package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/17
 */
@Service
public class CouponServiceImpl implements CouponService, CouponServiceForBl {

    @Autowired
    CouponMapper couponMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<Coupon> getCouponsByUserForBl(int userId) {
        try {
            return couponMapper.selectCouponByUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            // 创建优惠券
            Coupon coupon = new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());

            // 插入优惠券
            couponMapper.insertCoupon(coupon);

            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, List<Integer> userIdList) {
        try {
            for(int userId : userIdList) {
                couponMapper.insertCouponUser(couponId, userId);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("为多个用户赠送优惠券失败！");
        }
    }

    @Override
    public void deleteCouponUser(int couponId, int userId) {
        try {
            couponMapper.deleteCouponUser(couponId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Coupon getCouponById(int id) {
        try {
            return couponMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
