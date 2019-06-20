package com.example.cinema.blImpl.management.hall;

import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;

import java.util.ArrayList;
import java.util.List;

public class HallMapperStub { // implements HallMapper 用于集称测试 如果不注释implements就会导致自动autowire

    private List<Hall> halls = new ArrayList<>();

    public List<Hall> selectHalls() {
        return halls;
    }

    public Hall selectHallById(int hallId) {
        for(Hall localHall : this.halls) {
            if(localHall.getId() == hallId) {
                return localHall;
            }
        }
        return null;
    }

    public void insertOneHall(Hall hall) {
        hall.setId((int) (Math.random()*100));
        this.halls.add(hall);
    }

    public void updateOneHall(Hall hall) {
        for(Hall localHall : this.halls) {
            if(localHall.getId() == hall.getId()) {
                localHall.setColumn(hall.getColumn());
                localHall.setRow(hall.getRow());
                localHall.setName(hall.getName());
                break;
            }
        }
    }
}
