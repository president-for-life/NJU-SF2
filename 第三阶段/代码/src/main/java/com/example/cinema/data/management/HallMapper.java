package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 插入影厅
     *
     * @author 梁正川
     */
    int insertOneHall(Hall hall);

    /**
     * 更新影厅
     *
     * @author 梁正川
     */
    int updateOneHall(Hall hall);
}
