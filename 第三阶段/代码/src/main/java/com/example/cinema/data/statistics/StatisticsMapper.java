package com.example.cinema.data.statistics;

import com.example.cinema.po.AudiencePrice;
import com.example.cinema.po.Consumption;
import com.example.cinema.po.MovieScheduleTime;
import com.example.cinema.po.MovieTotalBoxOffice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 范佳杰
 */
@Mapper
public interface StatisticsMapper {

    /*================================================================================
    用户消费
     */

    /**
     * 查询消费达到一定金额的用户
     *
     * @author 石創烽
     */
    List<Consumption> selectConsumption(double amount);

    /**
     * 查询date日期每部电影的排片次数
     *
     * @author 范佳杰
     */
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询所有电影的总票房（包括已经下架的，降序排列）
     *
     * @author 范佳杰
     */
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    /**
     * 查询某天每个客户的购票金额
     *
     * @author 范佳杰
     */
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询最近days天内，最受欢迎的movieNum个电影
     *
     * @author 石創烽
     */
    List<MovieTotalBoxOffice> selectMoviePopular(
            @Param("daysAgoDate") Date daysAgoDate, @Param("date") Date date, @Param("movieNum") int movieNum
    );

    /**
     * 查询某天排片数量
     *
     * @author 石創烽
     */
    int selectScheduleCount(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询某天观众人次
     *
     * @author 石創烽
     */
    int selectAudienceCount(@Param("date") Date date, @Param("nextDate") Date nextDate);
}
