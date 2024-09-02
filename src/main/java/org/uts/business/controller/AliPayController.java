package org.uts.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.uts.exception.BusinessException;
import org.uts.result.RestResult;
import org.uts.service.order.PayService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description AliPay 接口类
 * @Author codBoy
 * @Date 2024/8/31 18:53
 */
@Slf4j
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    @Autowired
    private PayService payService;

    /*
     * 返回支付结果 - 同步
     */
    @GetMapping("/returnSync")
    public RestResult returnSync(HttpServletRequest request) throws BusinessException {
        String res = payService.returnSync(request);
        return RestResult.createSuccessfulRest();
    }

    /*
     * 返回支付结果 - 异步
     */
    @PostMapping("/notifyAsc")
    public RestResult notifyAsc(HttpServletRequest request) throws BusinessException {
        payService.notifyAsc(request);
        return RestResult.createSuccessfulRest();
    }
}
