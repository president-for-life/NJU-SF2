package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 范佳杰
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     *
     * @author 范佳杰
     */
    List<Hall> selectHalls();

    /**
     * 根据id查询影厅
     *
     * @author 范佳杰
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 插入影厅
     *
     * @author 徐志乐
     */
    void insertOneHall(Hall hall);

    /**
     * 更新影厅
     *
     * @author 徐志乐
     */
    void updateOneHall(Hall hall);
}
