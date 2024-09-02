package org.uts.business.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.uts.config.AliPayConfig;
import org.uts.exception.BusinessException;
import org.uts.global.constant.BusinessConstant;
import org.uts.global.constant.OrderStatusEnum;
import org.uts.global.errorCode.BusinessErrorCode;
import org.uts.service.order.OrderService;
import org.uts.service.order.PayService;
import org.uts.utils.AliPayUtils;
import org.uts.vo.order.OrderVo;
import org.uts.vo.order.RefundVo;
import org.uts.vo.pay.PayVo;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @Description 订单相关 实现类
 * @Author codBoy
 * @Date 2024/8/31 18:01
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private AliPayUtils aliPayUtils;

    @Reference
    private OrderService orderService;

    /*
     支付
     */
    @Override
    public String pay(PayVo payVo) throws BusinessException {
        //调用Alipay,发起支付
        try {
            return aliPayUtils.pay(payVo.getOrderId(), payVo.getProduceName(), payVo.getPrice());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException(BusinessErrorCode.SEND_PAY_FAILED);
        }
    }

    /*
     * 退款
     */
    @Override
    public boolean refund(RefundVo refundVo) throws BusinessException {
        try {
            //调用Alipay,发起退款
            return aliPayUtils.refund(refundVo);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException(BusinessErrorCode.SEND_REFUND_FAILED);
        }
    }

    /*
     * 返回支付结果 - 同步
     */
    @Override
    public String returnSync(Object o) throws BusinessException {
        //参数处理
        HttpServletRequest httpServletRequest = (HttpServletRequest) o;
        Map<String, String> paramsMap = getParamsMap(httpServletRequest.getParameterMap());
        log.info("Receive Return Sync Message From AliPay, Msg:{}", paramsMap);

        //阿里公钥验签
        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(paramsMap, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
            if(!verifyResult) {
                log.info("Signature AliPay Sync Message FAILED ...");
                throw new BusinessException(BusinessErrorCode.SIGNATURE_PAY_FAILED);
            }
            log.info("Signature AliPay Sync Message SUCCESS ...");

            //跳转到订单详情页面
            return "redirect:";
        } catch (AlipayApiException e) {
            log.info("Signature AliPay Sync Message FAILED ...");
            e.printStackTrace();
            throw new BusinessException(BusinessErrorCode.SIGNATURE_PAY_FAILED);
        }



    }

    /*
     * 返回支付结果 - 异步
     */
    @Override
    public String notifyAsc(Object o) throws BusinessException {
        //参数处理
        HttpServletRequest httpServletRequest = (HttpServletRequest) o;
        Map<String, String> paramsMap = getParamsMap(httpServletRequest.getParameterMap());
        log.info("Receive Return Sync Message From AliPay, Msg:{}", paramsMap);

        //阿里公钥验签
        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(paramsMap, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
            if(!verifyResult) {
                log.info("Signature AliPay Sync Message FAILED ...");
                throw new BusinessException(BusinessErrorCode.SIGNATURE_PAY_FAILED);
            }
            log.info("Signature AliPay Sync Message SUCCESS ...");

            //商户订单号
            String outTradeNo = httpServletRequest.getParameter("out_trade_no");
            //支付宝交易号
            String tradeNo = httpServletRequest.getParameter("trade_no");
            //交易状态
            String tradeStatus = httpServletRequest.getParameter("trade_status");
            //支付金额
            String totalAmount = httpServletRequest.getParameter("total_amount");

            if(tradeStatus.equals("TRADE_FINISHED")) {
                log.info("Order Finished, Order Number: {}", outTradeNo);
            } else if(tradeStatus.equals("TRADE_SUCCESS")) {
                //远程调用订单服务，更新订单状态
                log.info("Order Pay SUCCESS, Order Number: {}", outTradeNo);
                OrderVo orderVo = new OrderVo();
                orderVo.setOrderId(outTradeNo);
                orderVo.setTradeNumber(tradeNo);
                orderVo.setPrice(Float.parseFloat(totalAmount));
                orderVo.setStatus(OrderStatusEnum.PAYED_STATUS.getStatus());
                int n = orderService.updateOrder(orderVo);
                if(n <= 0) {
                    log.info("Order Status Update FAILED, Order Number: {}", outTradeNo);
                }
            }

            return "success";
        } catch (AlipayApiException e) {
            log.info("Signature AliPay Sync Message FAILED ...");
            e.printStackTrace();
            throw new BusinessException(BusinessErrorCode.SIGNATURE_PAY_FAILED);
        }
    }


    /*
     AliPay异步/同步请求参数处理
     */
    public Map<String, String> getParamsMap(Map<String, String[]> requestParamsMap) {
        Map<String, String> paramsMap = new HashMap<>();
        for (Iterator<String> iterator = requestParamsMap.keySet().iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParamsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + BusinessConstant.COMMA;
            }
            //乱码解决
            //valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            paramsMap.put(name, valueStr);
        }
        return paramsMap;
    }
}
