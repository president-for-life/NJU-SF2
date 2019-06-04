package com.example.cinema.bl.management;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleBatchDeleteForm;
import com.example.cinema.vo.ScheduleForm;
import com.example.cinema.vo.ScheduleViewForm;

import java.util.Date;

/**
 * @author 范佳杰
 * @date 2019/4/11 4:14 PM
 */
public interface ScheduleService {
    /**
     * 添加排片信息
     *
     * @author 范佳杰
     */
    ResponseVO addSchedule(ScheduleForm scheduleForm);

    /**
     * 查询包括从起始时间开始的7天排片计划
     *
     * @author 范佳杰
     */
    ResponseVO searchScheduleSevenDays(int hallId, Date startDate);

    /**
     * 设置排片对观众的可见的天数(全局设置,暂时只涉及天数)
     * 若设置7天，且今天是04-11，则观众可见04-11到04-17的排片信息，其他均不可见
     *
     * @author 范佳杰
     */
    ResponseVO setScheduleView(ScheduleViewForm scheduleViewForm);

    /**
     * 批量删除排片信息
     *
     * @author 范佳杰
     */
    ResponseVO deleteBatchOfSchedule(ScheduleBatchDeleteForm scheduleBatchDeleteForm);

    /**
     * 修改排片信息
     *
     * @author 范佳杰
     */
    ResponseVO updateSchedule(ScheduleForm scheduleForm);

    /**
     * 根据排片id获取排片信息
     *
     * @author 范佳杰
     */
    ResponseVO getScheduleById(int scheduleId);

    /**
     * 查询排片对观众的可见的天数
     *
     * @author 范佳杰
     */
    ResponseVO getScheduleView();

    /**
     * 某电影对观众可见的排片信息
     *
     * @author 范佳杰
     */
    ResponseVO searchAudienceSchedule(int movieId);

}
