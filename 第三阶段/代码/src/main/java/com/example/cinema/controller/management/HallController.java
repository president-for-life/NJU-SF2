package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 影厅管理
 *
 * @author 范佳杰，徐志乐
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall() {
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "/hall/add", method = RequestMethod.POST)
    public ResponseVO insertOneHall(@RequestBody HallForm hallForm){
        return hallService.insertOneHall(hallForm);
    }

    @PostMapping(value = "/hall/update")
    public ResponseVO updateOneHall(@RequestBody HallForm hallUpdateForm) {
        return hallService.updateOneHall(hallUpdateForm);
    }


}
