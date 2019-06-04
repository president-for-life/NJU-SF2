package com.example.cinema.bl.statistics;

import com.example.cinema.vo.ResponseVO;

import java.util.Date;

/**
 * @author 范佳杰
 * @date 2019/4/16 1:34 PM
 */
public interface StatisticsService {
    /**
     * 获取某日各影片排片率统计数据
     *
     * @author 范佳杰
     */
    ResponseVO getScheduleRateByDate(Date date);

    /**
     * 获取所有电影的累计票房（降序排序，且包含已下架的电影）
     *
     * @author 范佳杰
     */
    ResponseVO getTotalBoxOffice();

    /**
     * 客单价：（某天的客单价 = 当天观众购票所花金额 / 购票人次数）
     *
     * @author 范佳杰
     * @return 过去7天内每天客单价
     */
    ResponseVO getAudiencePriceSevenDays();

    /**
     * 获取所有电影某天的上座率
     * 上座率参考公式：假设某影城设有n个电影厅、m个座位数，相对上座率=观众人次÷放映场次÷m÷n×100%
     *
     * @author 石創烽
     * @author 梁正川
     */
    ResponseVO getMoviePlacingRateByDate(Date date);

    /**
     * 获取最近days天内，最受欢迎的movieNum个电影
     * 可以简单理解为最近days内票房越高的电影越受欢迎
     *
     * @author 石創烽
     * @author 梁正川
     */
    ResponseVO getPopularMovies(int days, int movieNum);

    /**
     * 获得消费达到一定金额的用户
     *
     * @author 石創烽
     */
    ResponseVO getConsumption(double amount);
}
