package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.VIPCard;

public interface VIPServiceForBl {
    /**
     * 根据id查找会员卡
     * @author 梁正川
     * @param id 会员卡id
     * @return po.VIPCard
     */
    VIPCard getVIPCardById(int id);
}
