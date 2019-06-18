package com.example.cinema.blImpl.sales;

import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketRefundStrategyForm;

import java.util.List;

public interface TicketRefundStrategyServiceForBl {

    ResponseVO addRefundStrategy(TicketRefundStrategyForm strategyForm);

    ResponseVO updateRefundStrategy(TicketRefundStrategyForm strategyForm);

    ResponseVO addRefundMovies(int refundStrategyId, List<Integer> movieIdList);

    ResponseVO removeRefundMovies(int refundStrategyId, List<Integer> movieIdList);

    ResponseVO searchAllRefundStrategy();

    ResponseVO getMoviesNotInRefundStrategy();

    ResponseVO getMoviesByRefundStrategy(int strategyId);
}
