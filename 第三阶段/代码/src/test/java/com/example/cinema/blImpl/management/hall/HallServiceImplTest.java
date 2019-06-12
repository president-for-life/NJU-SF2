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

    @Autowired
    private HallServiceImpl hallService = new HallServiceImpl();

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
    void getNumHalls() {

    }

    @Test
    void getNumSeats() {
    }

    @Test
    void insertOneHall() {
        try{
            hallService.insertOneHall(hallForm).getSuccess();
            System.out.println("insert successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("insert failed");
        }
        finally {
            System.out.println("insertHallTest over.");
        }
    }

    @Test
    void updateOneHall() {
    }
}