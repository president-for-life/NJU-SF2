package com.example.cinema.data.statistics;

import com.example.cinema.po.AudiencePrice;
import com.example.cinema.po.MovieScheduleTime;
import com.example.cinema.po.MovieTotalBoxOffice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:43 PM
 */
@Mapper
public interface StatisticsMapper {
    /**
     * 查询date日期每部电影的排片次数
     *
     * @param date 日期
     * @return po.MovieScheduleTime数组
     */
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询所有电影的总票房（包括已经下架的，降序排列）
     *
     * @return po.MovieTotalBoxOffice数组
     */
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    /**
     * 查询某天每个客户的购票金额
     *
     * @param date 日期
     * @return po.AudiencePrice数组
     */
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询最近days天内，最受欢迎的movieNum个电影
     *
     * @return po.MovieTotalBoxOffice数组
     * @author 梁正川
     */
    List<MovieTotalBoxOffice> selectMoviePopular(
            @Param("daysAgoDate") Date daysAgoDate, @Param("date") Date date, @Param("movieNum") int movieNum
    );

    /**
     * 查询某天排片数量
     *
     * @author 梁正川
     */
    int selectScheduleCount(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询某天观众人次
     *
     * @author 梁正川
     */
    int selectAudienceCount(@Param("date") Date date, @Param("nextDate") Date nextDate);
}
