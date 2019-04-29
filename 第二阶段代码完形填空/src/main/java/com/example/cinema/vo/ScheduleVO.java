package com.example.cinema.vo;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 4:05 PM
 */
public class ScheduleVO {
    private Date date;
    private List<ScheduleItem> scheduleItemList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ScheduleItem> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItem> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }
}
