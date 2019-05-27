package com.example.cinema.vo;

import com.example.cinema.po.Hall;

/**
 * @author 徐志乐
 * @date 2019/5/27 7:22 PM
 */
public class HallForm {

    private Integer id;
    private String name;
    private Integer row;
    private Integer column;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Hall getPO() {
        Hall hall = new Hall();
        hall.setId(this.getId());
        hall.setName(this.getName());
        hall.setColumn(this.getColumn());
        hall.setRow(this.getRow());
        return hall;
    }
}
