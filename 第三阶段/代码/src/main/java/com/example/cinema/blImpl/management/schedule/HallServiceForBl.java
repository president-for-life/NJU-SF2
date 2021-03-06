package com.example.cinema.blImpl.management.schedule;

import com.example.cinema.po.Hall;

/**
 * @author 范佳杰
 * @date 2019/4/28 12:27 AM
 */
public interface HallServiceForBl {
    /**
     * @author 范佳杰
     */
    Hall getHallById(int hallId);

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
