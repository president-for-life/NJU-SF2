package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
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
 * @author 范佳杰，徐志乐
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;

    /**
     * po.Hall数组转vo.HallVO数组
     */
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
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getNumHalls() {
        try {
            return hallMapper.selectAllHall().size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Integer getNumSeats() {
        try {
            int numSeats = 0;
            List<Hall> hallList = hallMapper.selectAllHall();
            for (Hall hall : hallList) {
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
            //TODO 最好判断是否有重名影厅
            hallMapper.insertOneHall(hallForm.getPO());
        } catch (Exception e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO updateOneHall(HallForm hallUpdateForm) {
        try {
            Hall hall = hallUpdateForm.getPO();
            //TODO 需要判断影厅当前是否已经被使用
            hallMapper.updateOneHall(hall);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


}
