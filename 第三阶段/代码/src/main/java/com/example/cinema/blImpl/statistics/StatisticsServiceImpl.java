package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.AudiencePrice;
import com.example.cinema.po.MovieScheduleTime;
import com.example.cinema.po.MovieTotalBoxOffice;
import com.example.cinema.vo.AudiencePriceVO;
import com.example.cinema.vo.MovieScheduleTimeVO;
import com.example.cinema.vo.MovieTotalBoxOfficeVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 范佳杰
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private HallServiceForBl hallServiceForBl;

    /**
     * 计算date所在当天00:00的日期
     *
     * @author 梁正川
     */
    private static Date getStartOfDate(Date date) {
        try {
            Date requireDate = date;
            if (requireDate == null) {
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));
            return requireDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得num天后的日期
     */
    private static Date getNumDayAfterDate(Date oldDate, int num) {
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }

    private static List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList) {
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for (MovieScheduleTime movieScheduleTime : movieScheduleTimeList) {
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }

    private static List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList) {
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for (MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList) {
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }

    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try {
            Date requireDate = getStartOfDate(date);
            Date nextDate = getNumDayAfterDate(requireDate, 1);

            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(
                    statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(
                    statisticsMapper.selectMovieTotalBoxOffice()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            Date today = getStartOfDate(new Date());
            Date startDate = getNumDayAfterDate(today, -6);

            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * @author 梁正川
     * @author 石創烽
     */
    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        try {
            Date requireDate = getStartOfDate(date);
            Date nextDate = getNumDayAfterDate(requireDate, 1);

            int numHalls = hallServiceForBl.getNumHalls();
            int numSeats = hallServiceForBl.getNumSeats();
            int numAudience = statisticsMapper.selectAudienceCount(requireDate, nextDate);
            int numSchedules = statisticsMapper.selectScheduleCount(requireDate, nextDate);

            double rate = (double) numAudience / (numHalls * numSeats * numSchedules);
            String res = (double) Math.round(rate * 10000) / 100 + ""; // 保留四位有效数字，如“66.67%”
            //////////////////////////////////////////////////////
            ////////////////////控制台测试信息////////////////////
            System.out.println(
                    "----------StatisticsService.getMoviePlacingRateByDate测试信息----------"
                            + "\n" + new SimpleDateFormat("yyyy-MM-dd").format(requireDate) + "的上座率：" + res
                            + "\n影厅数：" + numHalls
                            + "\n总座位数：" + numSeats
                            + "\n观众人次：" + numAudience
                            + "\n放映场次：" + numSchedules
            );
            ////////////////////控制台测试信息////////////////////
            //////////////////////////////////////////////////////

            return ResponseVO.buildSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * @author 梁正川
     * @author 石創烽
     */
    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        try {
            Date startOfToday = getStartOfDate(new Date());
            Date endOfToday = getNumDayAfterDate(startOfToday, 1);
            Date startOfDaysAgoDate = getNumDayAfterDate(endOfToday, -days);

            List<MovieTotalBoxOffice> moviePopularList
                    = statisticsMapper.selectMoviePopular(startOfDaysAgoDate, endOfToday, movieNum);

            //////////////////////////////////////////////////////
            ////////////////////控制台测试信息////////////////////
            System.out.println("----------StatisticsService.getPopularMovies测试信息----------");
            System.out.println("最近" + days + "天内最受欢迎的" + movieNum + "个电影：");
            for (int i = 0; i < moviePopularList.size(); i++) {
                MovieTotalBoxOffice movie = moviePopularList.get(i);
                System.out.println("第" + (i + 1) + "：" + movie.getName() + " 票房：" + movie.getBoxOffice());
            }
            ////////////////////控制台测试信息////////////////////
            //////////////////////////////////////////////////////

            return ResponseVO.buildSuccess(
                    movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(moviePopularList)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
