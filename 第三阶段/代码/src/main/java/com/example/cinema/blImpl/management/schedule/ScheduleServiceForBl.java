package com.example.cinema.blImpl.management.schedule;

import com.example.cinema.po.ScheduleItem;

import java.util.List;

/**
 * @author 范佳杰
 */
public interface ScheduleServiceForBl {
    /**
     * 查询所有涉及到movieIdList中电影的排片信息
     *
     * @author 范佳杰
     */
    List<ScheduleItem> getScheduleByMovieIdList(List<Integer> movieIdList);

    /**
     * @author 范佳杰
     */
    ScheduleItem getScheduleItemById(int id);

    /**
     * 获得某影厅正在进行和将来的排片的数量
     *
     * @author 梁正川
     */
    int getNumSchedules(int hallId);
}
