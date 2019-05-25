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
     * @param movieIdList 电影id数组
     * @return po.ScheduleItem数组
     */
    List<ScheduleItem> getScheduleByMovieIdList(List<Integer> movieIdList);

    /**
     * 根据id查找排片信息
     *
     * @param id 排片id
     * @return po.ScheduleItem
     */
    ScheduleItem getScheduleItemById(int id);
}
