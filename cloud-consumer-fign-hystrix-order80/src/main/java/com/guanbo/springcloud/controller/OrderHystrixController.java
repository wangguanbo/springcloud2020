package com.guanbo.springcloud.controller;

import com.guanbo.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentInfo_global_fallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/customer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/customer/payment/hystrix/timout/{id}")
    /*@HystrixCommand(fallbackMethod = "paymentInfo_timeOutBack" , commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand
    public String paymentInfo_timeOut(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_timeOut(id);
    }

    public String paymentInfo_timeOutBack(@PathVariable("id") Integer id){
        return "80 服务器繁忙，请稍后再试！";
    }

    public String paymentInfo_global_fallbackMethod(){
        return "80 global异常处理信息，请稍后再试！";
    }
}
