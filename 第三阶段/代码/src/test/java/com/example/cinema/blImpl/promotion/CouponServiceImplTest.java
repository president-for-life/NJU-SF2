package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DisplayName("CouponServiceImpl自底向上集成测试")
class CouponServiceImplTest {

    @Autowired
    CouponServiceImpl couponService;

    @Test
    void getValidCoupons() {
        List<Coupon> coupons =
                (List<Coupon>) couponService.getValidCoupons().getContent();
        assertEquals(3, coupons.size());
    }

    @Test
    void issueCoupon() {
        int user1 = 18;
        int user2 = 19;

        int coupon1 = 17;
        int coupon2 = 18;
        int coupon3 = 19;

        couponService.issueCoupon(
                Arrays.asList(coupon1, coupon2, coupon3),
                Arrays.asList(user1, user2)
        );

        List<Coupon> coupons
                = (List<Coupon>) couponService.getCouponsByUser(user1).getContent();
        List<Integer> couponIdList
                = coupons.stream()
                .map(coupon -> coupon.getId())
                .collect(Collectors.toList());
        assertTrue(couponIdList.contains(17));
        assertTrue(couponIdList.contains(18));
        assertTrue(couponIdList.contains(19));

        coupons = (List<Coupon>) couponService.getCouponsByUser(user2).getContent();
        couponIdList = coupons.stream()
                .map(coupon -> coupon.getId())
                .collect(Collectors.toList());
        assertTrue(couponIdList.contains(17));
        assertTrue(couponIdList.contains(18));
        assertTrue(couponIdList.contains(19));
    }
}