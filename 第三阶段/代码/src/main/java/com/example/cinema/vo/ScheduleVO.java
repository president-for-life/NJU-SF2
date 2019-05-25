package com.example.cinema.vo;

import java.util.Date;
import java.util.List;

/**
 * @author 范佳杰
 * @date 2019/4/12 4:05 PM
 */
public class ScheduleVO {
    private Date date;
    private List<ScheduleItemVO> scheduleItemList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ScheduleItemVO> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItemVO> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }
}
