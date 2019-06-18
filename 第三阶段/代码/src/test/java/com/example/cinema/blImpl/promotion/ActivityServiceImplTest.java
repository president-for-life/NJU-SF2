package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DisplayName("ActivityServiceImpl自底向上集成测试")
class ActivityServiceImplTest {

    @Autowired
    ActivityServiceImpl activityService;

    @Autowired
    CouponMapper couponMapper; // 用于验证 ActivityServiceImpl 的功能

    private static final int userId = 16;
    private static final int movieId = 26;

    @Test
    void participate() {
        activityService.participate(movieId, userId);

        List<Coupon> coupons = couponMapper.selectCouponByUser(userId);
        List<Integer> couponIdList = coupons.stream()
                .map(coupon -> coupon.getId())
                .collect(Collectors.toList());
        assertTrue(couponIdList.contains(17));
    }
}