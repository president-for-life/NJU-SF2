package com.example.cinema.data.management;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cinema.po.Hall;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@ExtendWith(SpringExtension.class) //导入spring测试框架
@SpringBootTest  //提供spring依赖注入
@Transactional  //事务管理，默认回滚,如果配置了多数据源记得指定事务管理器
@DisplayName("HallMapper单元测试")

/*
 * @author Xu
 */

class HallMapperTest {

    @Autowired
    private HallMapper hallMapper;
    private static Hall hall;

    @BeforeAll
    static void setHall(){
        hall = new Hall();
        hall.setName("Hall");
        hall.setRow(10);
        hall.setColumn(10);
    }

    @Test
    @DisplayName("insertOneHallTest")
    void insertOneHall() {
        List<Hall> hallList = hallMapper.selectHalls();
        hallMapper.insertOneHall(this.hall);
        List<Hall> hallList1 = hallMapper.selectHalls();

        //检测新的数据库里是否增加了一个hall
        assertEquals(hallList.size()+1,hallList1.size());

        //检测新增的hall数据是否正确
        boolean ifFail = true;
        for(Hall hallPO:hallList1){
            if(hallPO.getName().equals(hall.getName())){
                assertEquals(hall.getRow(),hallPO.getRow());
                assertEquals(hall.getColumn(),hallPO.getColumn());
                ifFail = false;
            }
        }
        if(ifFail == true){
            fail("未能找到相同名字的影厅");
        }

    }


    @Test
    @DisplayName("updateOneHallTest")
    void updateOneHall() {

        //插入一个hall，并从数据库中重新获取这个hall以获得它的id
        List<Hall> hallList = hallMapper.selectHalls();
        hallMapper.insertOneHall(this.hall);
        List<Hall> hallList1 = hallMapper.selectHalls();
        Hall insertedHall = null;
        for(Hall hallPO:hallList1){
            if(hallPO.getName().equals(hall.getName())){
                insertedHall = hallPO;
            }
        }

        //设置并初始化要插入的新hall。
        Hall newHall = new Hall();
        newHall.setId(insertedHall.getId());
        newHall.setRow(insertedHall.getRow()+1);
        newHall.setColumn(insertedHall.getColumn()-1);
        newHall.setName((insertedHall.getName()+"1"));

        //插入新hall
        hallMapper.updateOneHall(newHall);

        //通过id获取被插入的hall，观察被插入后的数据是否正确。
        Hall updatedHall = hallMapper.selectHallById(insertedHall.getId());
        assertEquals(newHall.getName(),updatedHall.getName());
        assertEquals(newHall.getColumn(),updatedHall.getColumn());
        assertEquals(newHall.getRow(),updatedHall.getRow());
    }
}