package com.example.cinema.blImpl.promotion;

import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.VIPCardChargeVO;
import com.example.cinema.vo.VIPCardWithStrategyVO;
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
@DisplayName("Test CouponServiceImpl")
class VIPServiceImplTest {

    @Autowired
    VIPServiceImpl vipService;

    private static final int userId = 16;
    private static final int strategyId = 11;

    /**
     * @return vipCardId
     */
    private int addCard() {
        vipService.addVIPCard(userId, strategyId);
        VIPCardWithStrategyVO vo = (VIPCardWithStrategyVO)
                vipService.getCardByUserId(userId).getContent();
        return vo.getId();
    }

    @Test
    void addVIPCard() {
        vipService.addVIPCard(userId, strategyId);
        VIPCardWithStrategyVO vo = (VIPCardWithStrategyVO)
                vipService.getCardByUserId(userId).getContent();
        assertEquals(strategyId, vo.getStrategy().getId());
    }

    @Test
    void charge() {
        int cardId = this.addCard();
        VIPCardChargeForm form = new VIPCardChargeForm();
        form.setVipCardId(cardId);
        form.setPayment(30);
        vipService.charge(form);

        List<VIPCardChargeVO> vo = (List<VIPCardChargeVO>)
                vipService.getChargeRecords(cardId).getContent();
        List<Double> chargeAmountList = vo.stream()
                .map(charge -> charge.getPayment())
                .collect(Collectors.toList());
        assertTrue(chargeAmountList.contains(30.0));

        List<Double> actualAmountList = vo.stream()
                .map(charge -> charge.getAmount())
                .collect(Collectors.toList());
        assertTrue(actualAmountList.contains(35.0));
    }

    @Test
    void pay() {
        int cardId = this.addCard();

        VIPCardChargeForm form = new VIPCardChargeForm();
        form.setVipCardId(cardId);
        form.setPayment(20);
        vipService.charge(form);

        VIPCardWithStrategyVO vo = (VIPCardWithStrategyVO)
                vipService.getCardByUserId(userId).getContent();
        double balanceOld = vo.getBalance(); // 现在有

        vipService.pay(userId, 15); // 花了15

        vo = (VIPCardWithStrategyVO)
                vipService.getCardByUserId(userId).getContent();
        assertEquals(balanceOld - 15, vo.getBalance());
    }
}