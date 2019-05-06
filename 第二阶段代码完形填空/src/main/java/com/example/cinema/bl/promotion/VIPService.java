package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    /**
     * 某用户获得会员卡
     * @param userId 用户id
     * @return vo.ResponseVO
     */
    ResponseVO addVIPCard(int userId);

    /**
     * 根据id获得某会员卡
     * @param id 会员卡id
     * @return vo.ResponseVO
     */
    ResponseVO getCardById(int id);

    /**
     * 获得会员卡信息
     * @return vo.ResponseVO
     */
    ResponseVO getVIPInfo();

    /**
     * 使用会员卡
     * @param vipCardForm 会员卡表单
     * @return vo.ResponseVO
     */
    ResponseVO charge(VIPCardForm vipCardForm);

    /**
     * 获得某用户的会员卡
     * @param userId 用户id
     * @return vo.ResponseVO
     */
    ResponseVO getCardByUserId(int userId);

}
