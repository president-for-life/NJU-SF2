package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.VIPCardStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/strategy/add")
    public ResponseVO addStrategy(@RequestBody VIPCardStrategyForm strategyForm) {
        return vipService.addStrategy(strategyForm);
    }

    @PostMapping("/strategy/update")
    public ResponseVO updateStrategy(@RequestBody VIPCardStrategyForm strategyForm) {
        return vipService.updateStrategy(strategyForm);
    }

    @GetMapping("/strategy/get")
    public ResponseVO getStrategy(@RequestParam int strategyId) {
        return vipService.getStrategy(strategyId);
    }

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId) {
        return vipService.addVIPCard(userId);
    }

    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId) {
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo() {
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardChargeForm vipCardChargeForm) {
        return vipService.charge(vipCardChargeForm);
    }


}
