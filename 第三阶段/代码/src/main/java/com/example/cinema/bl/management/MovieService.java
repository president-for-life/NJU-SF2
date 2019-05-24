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
     * 上架电影
     *
     * @param addMovieForm 电影表单
     * @return vo.ResponseVO
     */
    ResponseVO addMovie(MovieForm addMovieForm);

    /**
     * 根据id和userid搜索电影，可以知道这个用户是否点过想看这部电影
     *
     * @param id     电影id
     * @param userId 用户id
     * @return vo.ResponseVO
     */
    ResponseVO searchOneMovieByIdAndUserId(int id, int userId);

    /**
     * 搜索全部电影
     *
     * @return vo.ResponseVO
     */
    ResponseVO searchAllMovie();

    /**
     * 搜索全部电影(不包括已经下架的)
     *
     * @return vo.ResponseVO
     */
    ResponseVO searchOtherMoviesExcludeOff();

    /**
     * 根据关键字搜索电影
     *
     * @param keyword 关键词
     * @return vo.ResponseVO
     */
    ResponseVO getMovieByKeyword(String keyword);

    /**
     * 批量下架电影
     *
     * @param movieBatchOffForm 电影表单
     * @return vo.ResponseVO
     */
    ResponseVO pullOfBatchOfMovie(MovieBatchOffForm movieBatchOffForm);

    /**
     * 更新电影信息
     *
     * @param updateMovieForm 电影表单
     * @return vo.ResponseVO
     */
    ResponseVO updateMovie(MovieForm updateMovieForm);

}
