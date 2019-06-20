package com.example.cinema.data.statistics;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cinema.po.Consumption;
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
@DisplayName("AccountMapper单元测试")
class StatisticsMapperTest {
     @Autowired
    private StatisticsMapper statisticsMapper;

    @Test
    void selectConsumption() {
        List<Consumption> consumptionList= statisticsMapper.selectConsumption(300.0);
        assertEquals(3,consumptionList.size());
    }
}