package com.example.cinema.data.sales;

import com.example.cinema.po.TicketRefundStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //导入spring测试框架
@SpringBootTest  //提供spring依赖注入
@Transactional  //事务管理，默认回滚,如果配置了多数据源记得指定事务管理器
@DisplayName("TicketMapper单元测试")
class TicketMapperTest {
	@Autowired
	private TicketMapper ticketMapper;

	private static TicketRefundStrategy ticketRefundStrategy;
	private static boolean refundable = true;
	private static double ratio = 0.9;
	private static int time = 90;

	@BeforeAll
	static void setUp() {
		ticketRefundStrategy = new TicketRefundStrategy();
		ticketRefundStrategy.setRefundable(refundable);
		ticketRefundStrategy.setRatio(ratio);
		ticketRefundStrategy.setTime(time);
	}

	@Test
	@DisplayName("insertOneRefundStrategy")
	void insertOneRefundStrategy() {
		int beforeId = ticketMapper.selectRefundStrategies().size();  // 添加之前退票策略的数量

		ticketMapper.insertOneRefundStrategy(ticketRefundStrategy);

		int afterId = ticketMapper.selectRefundStrategies().size();  // 添加之后退票策略的数量

		assertEquals(1, afterId - beforeId);

		TicketRefundStrategy ticketRefundStrategy = ticketMapper.selectRefundStrategyById(afterId);
//		assertEquals(6,afterId);
		assertEquals(refundable, ticketRefundStrategy.getRefundable());
		assertEquals(ratio, ticketRefundStrategy.getRatio());
		assertEquals(time,ticketRefundStrategy.getTime());
	}

	@Test
	@DisplayName("updateOneRefundStrategy")
	void updateOneRefundStrategy() {
		ticketMapper.insertOneRefundStrategy(ticketRefundStrategy);
		int id = ticketMapper.selectRefundStrategies().size();

		TicketRefundStrategy newStrategy = new TicketRefundStrategy();
		newStrategy.setRefundable(refundable);
		newStrategy.setRatio(ratio*0.9);
		newStrategy.setTime(time+10);
		ticketMapper.updateOneRefundStrategy(newStrategy);

		TicketRefundStrategy ticketRefundStrategy = ticketMapper.selectRefundStrategyById(id);
		
		assertEquals(refundable, ticketRefundStrategy.getRefundable());
		assertEquals(ratio*0.9, ticketRefundStrategy.getRatio());
		assertEquals(time + 10, ticketRefundStrategy.getTime());
	}

	@Test
	@DisplayName("insertStrategyAndMovies")
	void insertStrategyAndMovies() {

	}

	@Test
	@DisplayName("deleteStrategyAndMovies")
	void deleteStrategyAndMovies() {
	}

	@Test
	@DisplayName("selectRefundStrategyById")
	void selectRefundStrategyById() {
	}

	@Test
	@DisplayName("selectMovieNotInRefundStrategy")
	void selectMovieNotInRefundStrategy() {


	}
}