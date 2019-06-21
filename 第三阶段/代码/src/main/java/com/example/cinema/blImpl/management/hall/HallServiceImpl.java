package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.blImpl.management.schedule.HallServiceForBl;
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

    private static final String repeatName =  "影厅名重复";

    /** 用于集成测试
    public HallServiceImpl() {}

    public HallServiceImpl(HallMapper mapper) {
        this.hallMapper = mapper;
    }
     **/

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
    /*
     * @author Xu
     * 插入一个新的影厅对象
     */

    @Override
    public ResponseVO insertOneHall(HallForm hallForm) {
        try {
            if(ifNameRepeat(hallForm.getName())){
                return ResponseVO.buildFailure(repeatName);
            }
            hallMapper.insertOneHall(hallForm.getPO());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    /*
     * @author Xu
     * 更新影厅信息
     */
    @Override
    public ResponseVO updateOneHall(HallForm hallUpdateForm) {
        try {
            //如果影厅名与原始影厅名不同,则有可能出现重名。
            if(!hallUpdateForm.getName().equals(hallMapper.selectHallById(hallUpdateForm.getId()).getName())){
                if(ifNameRepeat(hallUpdateForm.getName())){
                    return ResponseVO.buildFailure(repeatName);
                }
            }

            Hall hall = hallUpdateForm.getPO();
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

    private boolean ifNameRepeat(String name){
        List<Hall> hallList = hallMapper.selectHalls();
        for (Hall hall:hallList){
            if(hall.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
