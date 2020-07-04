package com.guanbo.springcloud.controller;

import com.guanbo.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String s = paymentService.paymentInfo_OK(id);
        log.info("***** result: " + s);
        return s;
    }

    @GetMapping("/payment/hystrix/timout/{id}")
    public String paymentInfo_timeOut(@PathVariable("id") Integer id){
        String s = paymentService.paymentInfo_timeOut(id);
        log.info("***** result: " + s);
        return s;
    }

    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String s = paymentService.paymentCircuitBreaker(id);
        return s;
    }



}
