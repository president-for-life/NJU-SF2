package com.example.cinema.data.statistics;

import com.example.cinema.po.DateLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李莹
 * @date 2019/3/23
 */
@Mapper
public interface MovieLikeMapper {

    /**
     * @author 李莹
     */
    int insertOneLike(@Param("movieId") int movieId, @Param("userId") int userId);

    /**
     * @author 李莹
     */
    int deleteOneLike(@Param("movieId") int movieId, @Param("userId") int userId);


    /**
     * @author 李莹
     */
    int selectLikeNums(@Param("movieId") int movieId);

    /**
     * @author 李莹
     */
    int selectLikeMovie(@Param("movieId") int movieId, @Param("userId") int userId);

    /**
     * 获得某个电影的喜爱的人数按日期统计
     *
     * @author 李莹
     */
    List<DateLike> getDateLikeNum(@Param("movieId") int movieId);
}
