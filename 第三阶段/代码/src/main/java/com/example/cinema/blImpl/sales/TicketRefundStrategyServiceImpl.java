package com.example.cinema.blImpl.sales;

import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.vo.MovieVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketRefundStrategyForm;
import com.example.cinema.vo.TicketRefundStrategyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketRefundStrategyServiceImpl implements TicketRefundStrategyServiceForBl {

    @Autowired
    TicketMapper ticketMapper;

    @Override
    public ResponseVO addRefundStrategy(TicketRefundStrategyForm strategyForm) {
        try {
            System.out.println(strategyForm.getRefundable()+" "+strategyForm.getRatio()+" "+strategyForm.getTime());

            ticketMapper.insertOneRefundStrategy(strategyForm.getPO());
            return ResponseVO.buildSuccess("新增退票策略成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("新增退票策略失败");
        }
    }

    @Override
    public ResponseVO updateRefundStrategy(TicketRefundStrategyForm strategyForm) {
        try {
            ticketMapper.updateOneRefundStrategy(strategyForm.getPO());
            return ResponseVO.buildSuccess("修改退票策略成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("修改退票策略失败");
        }
    }

    @Override
    public ResponseVO addRefundMovies(int refundStrategyId, List<Integer> movieIdList) {
        try {
            ticketMapper.insertStrategyAndMovies(refundStrategyId, movieIdList);
            return ResponseVO.buildSuccess("添加指定退票策略的电影列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("添加指定退票策略的电影列表失败");
        }
    }

    @Override
    public ResponseVO removeRefundMovies(int refundStrategyId, List<Integer> movieIdList) {
        try {
            ticketMapper.deleteStrategyAndMovies(refundStrategyId, movieIdList);
            return ResponseVO.buildSuccess("删除指定退票策略的电影列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("删除指定退票策略的电影列表失败");
        }
    }

    @Override
    public ResponseVO searchAllRefundStrategy() {
        try {
            List<TicketRefundStrategyVO> ticketRefundStrategyVOList = new ArrayList<>();
            ticketMapper.selectRefundStrategies().forEach(ticketRefundStrategy -> {
                ticketRefundStrategyVOList.add(new TicketRefundStrategyVO(ticketRefundStrategy));
            });
            return ResponseVO.buildSuccess(ticketRefundStrategyVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取已有的所有退票策略失败");
        }
    }

    @Override
    public ResponseVO getMoviesNotInRefundStrategy() {
        try {
            List<MovieVO> movieVOList = new ArrayList<>();
            ticketMapper.selectMovieNotInRefundStrategy().forEach(
                    movie -> {
                        movieVOList.add(new MovieVO(movie));
                    }
            );
            return ResponseVO.buildSuccess(movieVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取未被指定退票策略的电影列表失败");
        }
    }

    @Override
    public ResponseVO getMoviesByRefundStrategy(int strategyId) {
        try {
            List<MovieVO> movieVOList = new ArrayList<>();
            ticketMapper.selectRefundStrategyById(strategyId).getMovieList().forEach(
                    movie -> {
                        movieVOList.add(new MovieVO(movie));
                    }
            );
            return ResponseVO.buildSuccess(movieVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("获取使用指定退票策略的电影列表失败");
        }
    }
}
