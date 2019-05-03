package com.example.cinema.data.management;


import com.example.cinema.vo.ScheduleForm;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.ScheduleViewForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 4:18 PM
 */
@Mapper
public interface ScheduleMapper {
    /**
     * 插入一条排片信息
     * @param scheduleForm 排片表单
     * @return ？
     */
    int insertOneSchedule(ScheduleForm scheduleForm);

    /**
     * 查询从startDate开始到endDate为止的某hall的排片信息
     * @param hallId 影厅id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return po.ScheduleItem数组
     */
    List<ScheduleItem> selectSchedule(@Param("hallId") int hallId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * 查询起止时间是否有冲突(不包括与自身的冲突)
     * @param hallId 影厅id
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param id ？id
     * @return
     */
    List<ScheduleItem> selectScheduleConflictByHallIdAndTime(@Param("hallId") int hallId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("id") int id);

    /**
     * 插入观众可见排片限制
     * @param scheduleViewForm 排片可见表单
     * @return ？
     */
    int insertOneView(ScheduleViewForm scheduleViewForm);

    /**
     * 修改观众可见排片限制
     * @param scheduleViewForm 排片可见表单
     * @return ？
     */
    int updateOneView(ScheduleViewForm scheduleViewForm);

    /**
     * 查询view的记录数，以此判断后续操作是插入还是修改
     * @return view的记录数
     */
    int selectViewCount();

    /**
     * 批量删除排片信息
     * @param scheduleIdList 排片id数组
     * @return ？
     */
    int deleteScheduleBatch(List<Integer> scheduleIdList);

    /**
     * 批量查询排片信息
     * @param scheduleIdList 排片id数组
     * @return po.ScheduleItem数组
     */
    List<ScheduleItem> selectScheduleBatch(List<Integer> scheduleIdList);

    /**
     * 查询排片限制信息
     * @return ？
     */
    int selectView();

    /**
     * 根据id修改排片信息
     * @param scheduleForm 排片表单
     * @return ？
     */
    int updateScheduleById(ScheduleForm scheduleForm);


    /**
     * 根据id查找排片信息
     * @param id 排片id
     * @return po.ScheduleItem
     */
    ScheduleItem selectScheduleById(@Param("id") int id);

    /**
     * 查询所有涉及到movieIdList中电影的排片信息
     * @param movieIdList 排片id数组
     * @return po.ScheduleItem数组
     */
    List<ScheduleItem> selectScheduleByMovieIdList(List<Integer> movieIdList);

    /**
     * 查询movieId的所有排片信息
     * @param movieId 电影id
     * @return po.ScheduleItem数组
     */
    List<ScheduleItem> selectScheduleByMovieId(@Param("movieId") int movieId);

}
