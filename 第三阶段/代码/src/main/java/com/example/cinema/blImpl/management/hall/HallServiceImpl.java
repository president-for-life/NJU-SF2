package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 范佳杰
 * @author 徐志乐
 * @author 梁正川
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private ScheduleServiceForBl scheduleServiceForBl;

    private static List<HallVO> hallList2HallVOList(List<Hall> hallList) {
        List<HallVO> hallVOList = new ArrayList<>();
        for (Hall hall : hallList) {
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectHalls()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int hallId) {
        try {
            return hallMapper.selectHallById(hallId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getNumHalls() {
        try {
            List<Hall> halls = hallMapper.selectHalls();
            return (halls == null) ? 0 : halls.size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Integer getNumSeats() {
        try {
            int numSeats = 0;
            List<Hall> halls = hallMapper.selectHalls();
            for (Hall hall : halls) {
                numSeats += hall.getNumSeats();
            }
            return numSeats;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ResponseVO insertOneHall(HallForm hallForm) {
        try {
            // TODO 最好判断是否有重名影厅
            hallMapper.insertOneHall(hallForm.getPO());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public ResponseVO updateOneHall(HallForm hallUpdateForm) {
        try {
            Hall hall = hallUpdateForm.getPO();
            // TODO 需要判断影厅当前是否已经被使用
            int numSchedules = scheduleServiceForBl.getNumSchedules(hall.getId());
            if (numSchedules > 0) {
                return ResponseVO.buildFailure("正在使用中的影厅");
            }
            hallMapper.updateOneHall(hall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
