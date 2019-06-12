package com.example.cinema.vo;

/**
 * @author 李莹
 * @date 2019/4/18
 */
public class SeatForm {

    /**
     * 列号
     */
    private int columnIndex;

    /**
     * 排号
     */
    private int rowIndex;

    public SeatForm() {

    }

    public SeatForm(int columnIndex, int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
}
