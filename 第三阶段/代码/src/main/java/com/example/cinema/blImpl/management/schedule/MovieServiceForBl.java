package com.example.cinema.blImpl.management.schedule;

import com.example.cinema.po.Movie;

/**
 * @author fjj
 * @date 2019/4/28 12:29 AM
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
