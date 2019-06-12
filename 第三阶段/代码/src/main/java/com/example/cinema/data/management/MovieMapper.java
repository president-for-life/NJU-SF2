package com.example.cinema.data.management;

import com.example.cinema.po.Movie;
import com.example.cinema.vo.MovieForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 范佳杰
 * @date 2019/3/12 6:26 PM
 */
@Mapper
public interface MovieMapper {
    /**
     * @author 范佳杰
     */
    int insertOneMovie(MovieForm addMovieForm);

    /**
     * @author 范佳杰
     */
    Movie selectMovieById(@Param("id") int id);

    /**
     * @author 范佳杰
     */
    Movie selectMovieByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    /**
     * @author 范佳杰
     */
    List<Movie> selectAllMovie();

    /**
     * @author 范佳杰
     */
    List<Movie> selectOtherMoviesExcludeOff();

    /**
     * @author 范佳杰
     */
    List<Movie> selectMovieByKeyword(@Param("keyword") String keyword);

    /**
     * 批量更新电影状态
     *
     * @author 范佳杰
     */
    int updateMovieStatusBatch(List<Integer> movieIdList);

    /**
     * @author 范佳杰
     */
    int updateMovie(MovieForm updateMovieForm);
}
