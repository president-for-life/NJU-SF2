package com.example.cinema.data.management;

import com.example.cinema.vo.HallVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<HallVO> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    HallVO selectHallById(int hallId);
}
