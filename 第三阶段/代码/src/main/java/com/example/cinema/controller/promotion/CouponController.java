package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/16
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("/get/all")
    public ResponseVO getValidCoupons() {
        return couponService.getValidCoupons();
    }

    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId) {
        return couponService.getCouponsByUser(userId);
    }

    @PostMapping("/publish")
    public ResponseVO publishCoupon(@RequestBody CouponForm couponForm) {
        return couponService.addCoupon(couponForm);
    }

    @PostMapping("/issue")
    public ResponseVO issueCoupon(@PathVariable int couponId, @RequestBody List<Integer> userIdList) {
        return couponService.issueCoupon(couponId, userIdList);
    }
}
