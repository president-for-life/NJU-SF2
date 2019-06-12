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
     * @author 范佳杰
     */
    List<Hall> selectHalls();

    /**
     * @author 范佳杰
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * @author 徐志乐
     */
    void insertOneHall(Hall hall);

    /**
     * @author 徐志乐
     */
    void updateOneHall(Hall hall);
}
