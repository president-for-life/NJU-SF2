package com.example.cinema.bl.management;

import com.example.cinema.vo.MovieBatchOffForm;
import com.example.cinema.vo.MovieForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author 范佳杰
 * @date 2019/3/12 6:24 PM
 */
public interface MovieService {
    /**
     * @author 范佳杰
     */
    ResponseVO addMovie(MovieForm addMovieForm);

    /**
     * 根据id和userid搜索电影，可以知道这个用户是否点过想看这部电影
     *
     * @author 范佳杰
     */
    ResponseVO searchOneMovieByIdAndUserId(int movieId, int userId);

    /**
     * 搜索全部电影（包括已经下架的）
     *
     * @author 范佳杰
     */
    ResponseVO searchAllMovie();

    /**
     * 搜索全部电影（不包括已经下架的）
     *
     * @author 范佳杰
     */
    ResponseVO searchOtherMoviesExcludeOff();

    /**
     * @author 范佳杰
     */
    ResponseVO getMovieByKeyword(String keyword);

    /**
     * 批量下架电影
     *
     * @author 范佳杰
     */
    ResponseVO pullOfBatchOfMovie(MovieBatchOffForm movieBatchOffForm);

    /**
     * @author 范佳杰
     */
    ResponseVO updateMovie(MovieForm updateMovieForm);

}
