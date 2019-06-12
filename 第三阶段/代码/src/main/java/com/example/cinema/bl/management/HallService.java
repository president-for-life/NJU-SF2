package com.example.cinema.bl.management;

import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author 范佳杰
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * @author 范佳杰
     */
    ResponseVO searchAllHall();

    /**
     * @author 范佳杰
     */
    ResponseVO insertOneHall(HallForm hallForm);

    /**
     * @author 徐志乐
     */
    ResponseVO updateOneHall(HallForm hallUpdateForm);
}
