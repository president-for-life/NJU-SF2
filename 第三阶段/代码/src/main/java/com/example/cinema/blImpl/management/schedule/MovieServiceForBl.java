package com.example.cinema.blImpl.management.schedule;

import com.example.cinema.po.Movie;

/**
 * @author 范佳杰
 */
public interface MovieServiceForBl {
    /**
     * 根据id查找电影
     *
     * @param id 电影id
     * @return po.Movie
     */
    Movie getMovieById(int id);
}
