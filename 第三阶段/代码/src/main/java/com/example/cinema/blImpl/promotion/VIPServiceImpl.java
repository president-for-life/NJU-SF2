package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCardCharge;
import com.example.cinema.po.VIPCardStrategy;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/14
 */
@Service
public class VIPServiceImpl implements VIPService, VIPServiceForBl {
    @Autowired
    VIPCardMapper vipCardMapper;

    /*================================================================================
    会员卡策略
     */

    // TESTED
    @Override
    public ResponseVO addStrategy(VIPCardStrategyForm strategyForm) {
        VIPCardStrategy strategy = strategyForm.getPO();
        try {
            vipCardMapper.insertStrategy(strategy);
            return ResponseVO.buildSuccess("发布会员卡充值优惠策略成功");
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("发布会员卡充值优惠策略失败");
        }
    }

    // TESTED
    @Override
    public ResponseVO updateStrategy(VIPCardStrategyForm strategyForm) {
        try {
            int strategyId = strategyForm.getId();

            // 只有当没有会员卡使用该策略时才能修改该策略
            if(vipCardMapper.strategyUseCount(strategyId) == 0) {
                VIPCardStrategy strategy = strategyForm.getPO();
                vipCardMapper.updateStrategy(strategy);
                return ResponseVO.buildSuccess("修改会员卡充值优惠策略成功！");
            } else {
                return ResponseVO.buildFailure("存在会员卡使用该充值优惠策略！");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("修改会员卡充值优惠策略失败！");
        }
    }

    public ResponseVO removeStrategy(int strategyId) {
        try {
            vipCardMapper.deleteStrategy(strategyId);
            return ResponseVO.buildSuccess("删除会员卡充值优惠策略成功！");
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("删除会员卡充值优惠策略失败！");
        }
    }

    // TESTED
    @Override
    public ResponseVO getStrategy(int strategyId) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectStrategyById(strategyId));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获得会员卡充值优惠策略失败");
        }
    }

    // TESTED
    @Override
    public ResponseVO getAllStrategies() {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectStrategies());
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获得会员卡充值优惠策略失败");
        }
    }

    /*================================================================================
    会员卡
     */

    // TESTED
    @Override
    public ResponseVO addVIPCard(int userId, int strategyId) {
        try {
            VIPCard card = new VIPCard();
            card.setStrategyId(strategyId);
            card.setUserId(userId);
            card.setBalance(0);

            VIPCard oldCard = vipCardMapper.selectCardByUserId(userId);
            if(oldCard == null) { // 用户没有会员卡，增加会员卡
                vipCardMapper.insertOneCard(card);
            } else { // 用户已持有会员卡，切换会员卡策略
                vipCardMapper.updateCardStrategy(oldCard.getId(), strategyId);
            }

            return ResponseVO.buildSuccess(
                    vipCardMapper.selectCardByUserId(userId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    // TESTED
    @Override
    public ResponseVO getVIPCardStrategy(int strategyId) {
        try {
            VIPCardStrategy vipCardStrategy
                    = vipCardMapper.selectStrategyById(strategyId);
            VIPCardStrategyVO vipCardStrategyVO = vipCardStrategy.getVO();
            return ResponseVO.buildSuccess(vipCardStrategyVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取会员卡策略失败！");
        }
    }

    // TESTED
    @Override
    public ResponseVO charge(VIPCardChargeForm vipCardChargeForm) {
        try {
            // 获取会员卡和会员卡使用的策略
            VIPCard card = vipCardMapper.selectCardById(
                    vipCardChargeForm.getVipCardId()
            );

            VIPCardStrategy strategy = vipCardMapper.selectStrategyById(
                    card.getStrategyId()
            );

            // 根据付款金额计算会员卡增加的余额
            double amount = strategy.calculateAmount(
                    vipCardChargeForm.getPayment()
            );
            card.setBalance(card.getBalance() + amount);

            // 更新会员卡余额
            vipCardMapper.updateCardBalance(
                    card.getId(),
                    card.getBalance()
            );

            // 插入会员卡充值记录
            VIPCardCharge chargeRecord = new VIPCardCharge();
            chargeRecord.setVipCardId(card.getId());
            chargeRecord.setPayment(vipCardChargeForm.getPayment());
            chargeRecord.setAmount(amount);
            vipCardMapper.insertOneChargeRecord(chargeRecord);

            return ResponseVO.buildSuccess(card);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    // TODO UNTESTED
    @Override
    public boolean pay(int userId, double pay) {
        if (pay < 0) {
            throw new IllegalArgumentException("扣款数目不得小于0！");
        }

        try {
            VIPCard card = vipCardMapper.selectCardByUserId(userId);
            double balance = card.getBalance();

            if (balance >= pay) { // 余额充足，扣款成功
                vipCardMapper.updateCardBalance(card.getId(), balance - pay);
                return true;
            } else { // 余额不足，扣款失败
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // TESTED
    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            // 会员卡
            VIPCard card = vipCardMapper.selectCardByUserId(userId);
            if (card == null) {
                return ResponseVO.buildFailure("用户卡不存在");
            }

            // 会员卡使用的策略
            VIPCardStrategy strategy
                    = vipCardMapper.selectStrategyById(card.getStrategyId());

            VIPCardWithStrategyVO vo = new VIPCardWithStrategyVO(card, strategy) ;
            return ResponseVO.buildSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    // TESTED
    @Override
    public ResponseVO getChargeRecords(int vipCardId) {
        try {
            List<VIPCardCharge> chargeRecords = vipCardMapper.selectChargeRecordsByCard(vipCardId);
            return ResponseVO.buildSuccess(vipCardChargeList2VipCardChargeVOList(chargeRecords));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("查找会员卡充值记录失败！");
        }
    }

    private List<VIPCardChargeVO> vipCardChargeList2VipCardChargeVOList(List<VIPCardCharge> vipCardCharges) {
        List<VIPCardChargeVO> vipCardChargeVOS = new ArrayList<>();
        for (VIPCardCharge vipCardCharge : vipCardCharges) {
            vipCardChargeVOS.add(new VIPCardChargeVO(vipCardCharge));
        }
        return vipCardChargeVOS;
    }
}
