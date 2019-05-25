package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCardCharge;
import com.example.cinema.po.VIPCardStrategy;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.VIPCardStrategyForm;
import com.example.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResponseVO updateStrategy(VIPCardStrategyForm strategyForm) {
        // TODO 考虑对已购买该会员卡的影响。
        VIPCardStrategy strategy = strategyForm.getPO();
        try {
            vipCardMapper.updateStrategy(strategy);
            return ResponseVO.buildSuccess("修改会员卡充值优惠策略成功");
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("修改会员卡充值优惠策略失败");
        }
    }

    public ResponseVO getStrategy(int strategyId) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectStrategyById(strategyId));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获得会员卡充值优惠策略失败");
        }
    }

    /*================================================================================
    会员卡
     */

    @Override
    public ResponseVO addVIPCard(int userId) {
        // TODO
        // 创建会员卡
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);

        // 插入会员卡po
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        // TODO
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        vipInfoVO.setDescription(VIPCard.description);
        vipInfoVO.setPrice(VIPCard.price);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardChargeForm vipCardChargeForm) {
        // TODO
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardChargeForm.getVipCardId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }

        // 根据付款金额计算会员卡增加的余额
        double balance = vipCard.calculate(vipCardChargeForm.getAmount());
        vipCard.setBalance(vipCard.getBalance() + balance);

        // 更新会员卡余额
        try {
            vipCardMapper.updateCardBalance(vipCardChargeForm.getVipCardId(), vipCard.getBalance());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public boolean pay(int userId, double pay) {
        if (pay < 0) {
            throw new IllegalArgumentException("扣款数目不得小于0！");
        }

        try {
            VIPCard card = vipCardMapper.selectCardByUserId(userId);
            double balance = card.getBalance();

            //////////////////////////////////////////////////////
            ////////////////////控制台测试信息////////////////////
            System.out.println("扣款前余额：" + balance);
            ////////////////////控制台测试信息////////////////////
            //////////////////////////////////////////////////////

            if (balance >= pay) { // 余额充足，扣款成功
                vipCardMapper.updateCardBalance(card.getId(), balance - pay);

                //////////////////////////////////////////////////////
                ////////////////////控制台测试信息////////////////////
                card = vipCardMapper.selectCardByUserId(userId);
                balance = card.getBalance();
                System.out.println("扣款后余额：" + balance);
                ////////////////////控制台测试信息////////////////////
                //////////////////////////////////////////////////////

                return true;
            } else { // 余额不足，扣款失败
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if (vipCard == null) {
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    public List<VIPCardCharge> getChargeRecords(int userId) {
        // TODO
        return null;
    }
}
