package com.example.cinema.data.sales;

import com.example.cinema.po.Movie;
import com.example.cinema.po.TicketRefundStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //导入spring测试框架
@SpringBootTest  //提供spring依赖注入
@Transactional  //事务管理，默认回滚,如果配置了多数据源记得指定事务管理器
@DisplayName("TicketMapper单元测试")
/**
 * 该类的方法需要一个个单独进行测试
 */
class TicketMapperTest {
	@Autowired
	private TicketMapper ticketMapper;

	private static TicketRefundStrategy ticketRefundStrategy;
	private static boolean refundable = true;
	private static double ratio = 0.9;
	private static int time = 90;
	private static List<TicketRefundStrategy> before;
	private static List<Integer> beforeIds;
	private static List<TicketRefundStrategy> after;
	private static List<Integer> afterIds;

	@BeforeAll
	static void setUp() {
		beforeIds = new ArrayList<>();
		afterIds = new ArrayList<>();
		ticketRefundStrategy = new TicketRefundStrategy();
		ticketRefundStrategy.setRefundable(refundable);
		ticketRefundStrategy.setRatio(ratio);
		ticketRefundStrategy.setTime(time);
	}

	@Test
	@DisplayName("insertOneRefundStrategy")
	void insertOneRefundStrategy() {
		// 在对数据库操作前，数据库中原有的退票策略
		before = ticketMapper.selectRefundStrategies();
		before.forEach(
				strategy->{
					beforeIds.add(strategy.getId());
				}
		);

		ticketMapper.insertOneRefundStrategy(ticketRefundStrategy);

		after = ticketMapper.selectRefundStrategies();
		after.forEach(
				strategy->{
					afterIds.add(strategy.getId());
				}
		);
		afterIds.removeAll(beforeIds);  // after中只剩下了被insert的退票策略

		assertEquals(1,afterIds.size());

		int id = afterIds.get(0);
		TicketRefundStrategy temp = ticketMapper.selectRefundStrategyById(id);
		assertEquals(refundable, temp.getRefundable());
		assertEquals(ratio, temp.getRatio());
		assertEquals(time, temp.getTime());
	}

	@Test
	@DisplayName("updateOneRefundStrategy")
	void updateOneRefundStrategy() {
		// 在对数据库操作前，数据库中原有的退票策略
		before = ticketMapper.selectRefundStrategies();
		before.forEach(
				strategy->{
					beforeIds.add(strategy.getId());
				}
		);

		ticketMapper.insertOneRefundStrategy(ticketRefundStrategy);

		after = ticketMapper.selectRefundStrategies();
		after.forEach(
				strategy->{
					afterIds.add(strategy.getId());
				}
		);
		afterIds.removeAll(beforeIds);  // after中只剩下了被insert的退票策略

		assertEquals(1,afterIds.size());

		int id = afterIds.get(0);

		// 修改指定id的退票策略
		TicketRefundStrategy newStrategy = new TicketRefundStrategy();
		newStrategy.setId(id);
		newStrategy.setRefundable(true);
		newStrategy.setRatio(0.999);
		newStrategy.setTime(10);
		ticketMapper.updateOneRefundStrategy(newStrategy);

		TicketRefundStrategy afterUpdate = ticketMapper.selectRefundStrategyById(id);

		assertEquals(true, afterUpdate.getRefundable());
		assertEquals(0.999, afterUpdate.getRatio());
		assertEquals(10, afterUpdate.getTime());
	}

	@Test
	@DisplayName("insertStrategyAndMovies")
	void insertStrategyAndMovies() {
		// 在对数据库操作前，数据库中原有的退票策略
		before = ticketMapper.selectRefundStrategies();
		before.forEach(
				strategy->{
					beforeIds.add(strategy.getId());
				}
		);

		ticketMapper.insertOneRefundStrategy(ticketRefundStrategy);

		after = ticketMapper.selectRefundStrategies();
		after.forEach(
				strategy->{
					afterIds.add(strategy.getId());
				}
		);
		afterIds.removeAll(beforeIds);  // after中只剩下了被insert的退票策略

		assertEquals(1,afterIds.size());

		int id = afterIds.get(0);
		TicketRefundStrategy beforeInsert = ticketMapper.selectRefundStrategyById(id);  // temp中存放新添加的退票策略，暂时还没有对应的电影

		assertEquals(0,beforeInsert.getMovieList().size());  // 验证暂时没有对应的电影

		// movieId: 24-28
		List<Integer> movieIdList = Arrays.asList(24, 25, 26);
		ticketMapper.insertStrategyAndMovies(id, movieIdList);
		TicketRefundStrategy afterInsert = ticketMapper.selectRefundStrategyById(id);

//		assertEquals(movieIdList.size(), afterInsert.getMovieList().size());
		List<Integer> tempIds = new ArrayList<>();  // 记录修改后的电影ids
		List<Movie> tempMovies = afterInsert.getMovieList();
		tempMovies.forEach(
				movie -> {
					tempIds.add(movie.getId());
				}
		);
		assertEquals(movieIdList,tempIds);
	}

	@Test
	@DisplayName("deleteStrategyAndMovies")
	void deleteStrategyAndMovies() {
		// 在对数据库操作前，数据库中原有的退票策略
		before = ticketMapper.selectRefundStrategies();
		before.forEach(
				strategy->{
					beforeIds.add(strategy.getId());
				}
		);

		ticketMapper.insertOneRefundStrategy(ticketRefundStrategy);

		after = ticketMapper.selectRefundStrategies();
		after.forEach(
				strategy->{
					afterIds.add(strategy.getId());
				}
		);
		afterIds.removeAll(beforeIds);  // after中只剩下了被insert的退票策略

		assertEquals(1,afterIds.size());

		int id = afterIds.get(0);
		TicketRefundStrategy beforeInsert = ticketMapper.selectRefundStrategyById(id);  // temp中存放新添加的退票策略，暂时还没有对应的电影

		assertEquals(0,beforeInsert.getMovieList().size());  // 验证暂时没有对应的电影

		// movieId: 24-28
		List<Integer> movieIdList = Arrays.asList(24, 25, 26);
		ticketMapper.insertStrategyAndMovies(id, movieIdList);
		TicketRefundStrategy afterInsert = ticketMapper.selectRefundStrategyById(id);

		List<Integer> tempIds = new ArrayList<>();  // 记录修改后的电影ids
		List<Movie> tempMovies = afterInsert.getMovieList();
		tempMovies.forEach(
				movie -> {
					tempIds.add(movie.getId());
				}
		);
		assertEquals(movieIdList,tempIds);  // 验证电影列表已经被添加进对应的退票策略

		// 开始删除
		ticketMapper.deleteStrategyAndMovies(id, movieIdList);  // 把添加的电影列表从指定的退票策略中删除
		assertEquals(0,ticketMapper.selectRefundStrategyById(id).getMovieList().size());

	}

	/**
	 * 这个测试用例会随着数据库中的数据的变化而变化
	 */
	@Test
	@DisplayName("selectMovieNotInRefundStrategy")
	void selectMovieNotInRefundStrategy() {
		// 根据数据库中已有的退票策略进行测试
		List<Movie> movies = ticketMapper.selectMovieNotInRefundStrategy();
		List<Integer> movieIds = new ArrayList<>();
		movies.forEach(
				movie -> {
					movieIds.add(movie.getId());
				}
		);

		assertEquals(movieIds,Arrays.asList());  // 就数据库当前的状态而言，所有的电影都有对应的退票策略
	}
}