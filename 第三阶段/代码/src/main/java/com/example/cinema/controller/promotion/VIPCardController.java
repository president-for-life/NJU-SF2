package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.VIPCardStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李莹
 * @date 2019/4/14
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

    @DeleteMapping("/strategy/remove")
    public ResponseVO removeStrategy(@RequestParam int strategyId) {
        return vipService.removeStrategy(strategyId);
    }

    @GetMapping("/strategy/get")
    public ResponseVO getStrategy(@RequestParam int strategyId) {
        return vipService.getStrategy(strategyId);
    }

    @GetMapping("/strategy/get/all")
    public ResponseVO getAllStrategies() {
        return vipService.getAllStrategies();
    }

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId, @RequestParam int strategyId) {
        return vipService.addVIPCard(userId, strategyId);
    }

    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId) {
        return vipService.getCardByUserId(userId);
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardChargeForm vipCardChargeForm) {
        return vipService.charge(vipCardChargeForm);
    }

    @GetMapping("/charge/records")
    public ResponseVO getChargeRecords(@RequestParam int vipCardId) {
        return vipService.getChargeRecords(vipCardId);
    }
}
