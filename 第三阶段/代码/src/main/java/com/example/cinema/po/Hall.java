package com.example.cinema.po;

/**
 * @author 范佳杰
 * @date 2019/4/28 5:09 PM
 */
public class Hall {

    /**
     * 影厅id
     */
    private Integer id;

    /**
     * 影厅名
     */
    private String name;

    /**
     * 排数
     */
    private Integer row;

    /**
     * 列数
     */
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

    /**
     * 获得影厅座位数
     *
     * @author 梁正川
     */
    public Integer getNumSeats() {
        return this.getRow() * this.getColumn();
    }
}
