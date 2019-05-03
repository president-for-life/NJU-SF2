package com.example.cinema.bl.statistics;

import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/28 3:07 PM
 */
public interface MovieLikeService {
    /**
     * 想看电影
     * @param userId 用户id
     * @param movieId 电影id
     * @return ResponseVO
     */
    ResponseVO likeMovie(int userId, int movieId);

    /**
     * 取消想看电影
     * @param userId 用户id
     * @param movieId 电影id
     * @return ResponseVO
     */
    ResponseVO unLikeMovie(int userId,int movieId);

    /**
     * 统计想看电影的人数
     * @param movieId 电影id
     * @return ResponseVO
     */
    ResponseVO getCountOfLikes(int movieId);

    /**
     * 获得电影每日的想看人数
     * @param movieId 电影id
     * @return ResponseVO
     */
    ResponseVO getLikeNumsGroupByDate(int movieId);
}
