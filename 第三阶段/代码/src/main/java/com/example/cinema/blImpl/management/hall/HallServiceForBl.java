package com.example.cinema.blImpl.management.hall;

import com.example.cinema.po.Hall;

/**
 * @author 范佳杰
 * @date 2019/4/28 12:27 AM
 */
public interface HallServiceForBl {
    /**
     * 搜索影厅
     *
     * @param id 影厅id
     * @return po.Hall
     */
    Hall getHallById(int id);

    /**
     * 影厅数目
     *
     * @author 梁正川
     */
    Integer getNumHalls();

    /**
     * 所有座位数目
     *
     * @author 梁正川
     */
    Integer getNumSeats();
}
