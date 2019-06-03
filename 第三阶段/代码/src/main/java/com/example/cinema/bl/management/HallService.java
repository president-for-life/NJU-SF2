package com.example.cinema.bl.management;

import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author 范佳杰
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     *
     * @author 范佳杰
     */
    ResponseVO searchAllHall();

    /**
     * 插入一个新的影厅
     *
     * @author 范佳杰
     */
    ResponseVO insertOneHall(HallForm hallForm);

    /**
     * 更新一个影厅信息
     *
     * @author 徐志乐
     */
    ResponseVO updateOneHall(HallForm hallUpdateForm);
}
