package com.example.cinema.vo;

/**
 * @author fjj
 * @date 2019/4/16 2:48 PM
 */
public class MovieScheduleTimeVO {
    private Integer movieId;
    /**
     * 排片次数
     */
    private Integer time;
    private String name;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
