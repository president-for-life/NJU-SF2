package com.example.cinema.data.management;


import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.ScheduleForm;
import com.example.cinema.vo.ScheduleViewForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 范佳杰
 * @date 2019/4/11 4:18 PM
 */
@Mapper
public interface ScheduleMapper {
    /**
     * @author 范佳杰
     */
    int insertOneSchedule(ScheduleForm scheduleForm);

    /**
     * 查询从startDate开始到endDate为止的某hall的排片信息
     *
     * @author 范佳杰
     */
    List<ScheduleItem> selectSchedule(@Param("hallId") int hallId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * 查询起止时间是否有冲突(不包括与自身的冲突)
     *
     * @author 范佳杰
     */
    List<ScheduleItem> selectScheduleConflictByHallIdAndTime(@Param("hallId") int hallId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("id") int id);

    /**
     * 插入观众可见排片限制
     *
     * @author 范佳杰
     */
    int insertOneView(ScheduleViewForm scheduleViewForm);

    /**
     * 修改观众可见排片限制
     *
     * @author 范佳杰
     */
    int updateOneView(ScheduleViewForm scheduleViewForm);

    /**
     * 查询view的记录数，以此判断后续操作是插入还是修改
     *
     * @author 范佳杰
     * @return view的记录数
     */
    int selectViewCount();

    /**
     * 批量删除排片信息
     *
     * @author 范佳杰
     */
    int deleteScheduleBatch(List<Integer> scheduleIdList);

    /**
     * 批量查询排片信息
     *
     * @author 范佳杰
     */
    List<ScheduleItem> selectScheduleBatch(List<Integer> scheduleIdList);

    /**
     * 查询排片限制信息
     *
     * @author 范佳杰
     * @return 排片对观众的可见的天数
     */
    int selectView();

    /**
     * @author 范佳杰
     */
    int updateScheduleById(ScheduleForm scheduleForm);


    /**
     * @author 范佳杰
     */
    ScheduleItem selectScheduleById(@Param("id") int id);

    /**
     * 查询所有涉及到movieIdList中电影的排片信息
     *
     * @author 范佳杰
     */
    List<ScheduleItem> selectScheduleByMovieIdList(List<Integer> movieIdList);

    /**
     * @author 范佳杰
     */
    List<ScheduleItem> selectScheduleByMovieId(@Param("movieId") int movieId);

    /**
     * 某影厅正在进行和将来的排片的数量
     *
     * @author 梁正川
     */
    int selectNumSchedules(@Param("hallId") int hallId);
}
