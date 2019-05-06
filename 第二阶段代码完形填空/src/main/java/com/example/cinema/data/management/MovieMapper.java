package com.example.cinema.data.management;

import com.example.cinema.po.Movie;
import com.example.cinema.vo.MovieForm;
import com.example.cinema.vo.MovieVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/3/12 6:26 PM
 */
@Mapper
public interface MovieMapper {
    /**
     * 插入一条电影信息
     * @param addMovieForm 电影表单（使用表单和数据层打交道，很奇怪）
     */
    int insertOneMovie(MovieForm addMovieForm);

    /**
     * 根据id查找电影
     * @param id 电影id
     * @return po.Movie
     */
    Movie selectMovieById(@Param("id") int id);

    /**
     * 根据id和userId查找电影
     * @param id 电影id
     * @param userId 用户id
     * @return po.Movie
     */
    Movie selectMovieByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    /**
     * 展示所有电影
     * @return po.Movie数组
     */
    List<Movie> selectAllMovie();

    /**
     * 展示所有电影(不包括已经下架的)
     * @return po.Movie数组
     */
    List<Movie> selectOtherMoviesExcludeOff();

    /**
     * 根据关键字搜索电影
     * @param keyword 关键字
     * @return po.Movie数组
     */
    List<Movie> selectMovieByKeyword(@Param("keyword") String keyword);

    /**
     * 批量更新电影状态
     * @param movieIdList 电影id数组
     */
    int updateMovieStatusBatch(List<Integer> movieIdList);

    /**
     * 修改电影
     * @param updateMovieForm 电影表单
     */
    int updateMovie(MovieForm updateMovieForm);
}
