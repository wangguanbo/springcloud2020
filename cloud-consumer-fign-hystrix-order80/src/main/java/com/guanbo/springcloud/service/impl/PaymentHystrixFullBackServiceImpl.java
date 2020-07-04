package com.guanbo.springcloud.service.impl;

import com.guanbo.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixFullBackServiceImpl implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务器异常： fullback paymentInfo_OK";
    }

    @Override
    public String paymentInfo_timeOut(Integer id) {
        return "服务器异常： fullback paymentInfo_timeOut";
    }
}
