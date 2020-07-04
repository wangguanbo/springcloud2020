package com.guanbo.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_OK,id : " + id + " 嗯哼";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_timeOutHandler",commandProperties = {
            //超时走回路
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_timeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_timeOut,id : " + id + " 嗯哼";
    }

    public String paymentInfo_timeOutHandler(Integer id){
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_timeOut,id : " + id + " 系统繁忙，稍后再试";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率到多少后断开
    }
    )
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (0> id) {
            throw  new RuntimeException("** id 不能为负数");
        }
        String uuid = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + "调用成功：流水号：" + uuid;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试试 id： " + id;
    }

}
