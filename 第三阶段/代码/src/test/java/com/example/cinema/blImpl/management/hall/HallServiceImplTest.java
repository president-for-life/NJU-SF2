package com.example.cinema.blImpl.management.hall;

import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HallServiceImplTest {

    /**
    @Autowired
    private HallServiceImpl hallService = new HallServiceImpl(new HallMapperStub()); // 桩

    private static HallForm hallForm;

    @BeforeAll
    static void setUp(){
        System.out.println("Hall Test Begin");
        hallForm = new HallForm();
        hallForm.setColumn(2);
        hallForm.setRow(2);
        hallForm.setName("Test");
    }

    @Test
    void insertOneHall() {
        int initialSize = hallService.getNumHalls();
        hallService.insertOneHall(hallForm);
        int afterSize = hallService.getNumHalls();
        assertEquals(initialSize + 1, afterSize);
    }

    @Test
    void updateOneHall() {
        hallService.insertOneHall(hallForm);
        List<HallVO> halls = (List<HallVO>) hallService.searchAllHall().getContent();

        HallVO updatedHall = halls.get(0);

        int id = updatedHall.getId();
        hallForm.setId(id);
        hallForm.setColumn(16);
        hallForm.setName("xx");
        hallService.updateOneHall(hallForm);

        halls = (List<HallVO>) hallService.searchAllHall().getContent();
        for(HallVO hall : halls) {
            if(hall.getName().equals("xx")) {
                assertEquals(16, hall.getColumn());
            }
        }
    }
    **/
}