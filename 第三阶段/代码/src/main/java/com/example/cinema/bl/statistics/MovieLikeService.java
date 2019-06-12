package com.example.cinema.bl.statistics;

import com.example.cinema.vo.ResponseVO;

/**
 * @author 范佳杰
 * @date 2019/4/28 3:07 PM
 */
public interface MovieLikeService {
    /**
     * @author 范佳杰
     */
    ResponseVO likeMovie(int userId, int movieId);

    /**
     * @author 范佳杰
     */
    ResponseVO unLikeMovie(int userId, int movieId);

    /**
     * @author 范佳杰
     */
    ResponseVO getCountOfLikes(int movieId);

    /**
     * 获得电影每日的想看人数
     *
     * @author 范佳杰
     */
    ResponseVO getLikeNumsGroupByDate(int movieId);
}
