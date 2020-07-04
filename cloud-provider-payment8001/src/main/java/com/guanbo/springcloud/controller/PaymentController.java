package com.guanbo.springcloud.controller;

import com.guanbo.springcloud.entities.CommonResult;
import com.guanbo.springcloud.entities.Payment;
import com.guanbo.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("******插入结果： " + result);
        if (result > 0) {
            return new CommonResult(200,"插入数据库成功 serverPort" + serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping (value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id ){
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("******查询结束： " + paymentById + "哈哈哈哈");
        if ( null != paymentById) {
            return new CommonResult(200,"查询成功 serverPort" + serverPort,paymentById);
        }else {
            return new CommonResult(444,"没有对应记录" + id,null);
        }
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String timeout()  {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
