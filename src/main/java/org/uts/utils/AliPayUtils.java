package org.uts.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.uts.config.AliPayConfig;
import org.uts.vo.order.RefundVo;

/**
 * @Description 阿里巴巴支付 接入
 * @Author codBoy
 * @Date 2024/8/31 16:38
 */
@Component
@Slf4j
public class AliPayUtils {

    //创建alipay客户端
    AlipayClient alipayClient = AliPayConfig.getAlipayClient();

    /*
    向支付宝发起下单支付请求
    @param orderNumber 订单编号
    @param price 价格
    @param productName 商品名称
     */
    public String pay(String orderNumber, String productName, Float price) throws AlipayApiException {

        //创建请求
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(AliPayConfig.NOTIFY_URL);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(AliPayConfig.RETURN_URL);
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", orderNumber);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", price);
        //订单标题，不可使用特殊符号
        bizContent.put("subject", productName);

        /******可选参数******/
        //手机网站支付默认传值QUICK_WAP_WAY
        //bizContent.put("product_code", "QUICK_WAP_WAY");
        //bizContent.put("time_expire", "2022-08-01 22:00:00");

        //// 商品明细信息，按需传入
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        request.setBizContent(bizContent.toString());
        //发起支付请求
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request,"POST");

        // 如果需要返回GET请求，请使用
        // AlipayTradeWapPayResponse response = alipayClient.pageExecute(request,"GET");
        String pageRedirectionData = response.getBody();
        System.out.println(pageRedirectionData);

        if(response.isSuccess()){
            log.info("[支付宝支付]发起支付宝支付成功");
        } else {
            log.info("[支付宝支付]发起支付宝支付失败");
        }
        return pageRedirectionData;
    }

    /*
    向支付宝发起退款请求
     */
    public boolean refund(RefundVo refundVo) throws AlipayApiException {
        //设置请求参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        request.setBizModel(model);
        model.setOutTradeNo(refundVo.getOrderId());
        model.setTradeNo(refundVo.getTradeNumber());
        model.setRefundReason(refundVo.getReason());
        model.setRefundAmount(String.valueOf(refundVo.getMoney()));

        //发起退款请求
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        //先根据fund_change判断是否退款成功
        //退款是否成功可以根据同步响应的 fund_change 参数来判断，
        //fund_change 表示本次退款是否发生了资金变化，返回 Y 表示退款成功，返回 N 则表示本次退款未发生资金变动 。
        if (response.isSuccess() && "Y".equals(response.getFundChange())) {
            log.info("[支付宝退款]支付宝发起退款成功,订单ID：{}", refundVo.getOrderId());
            return true;
        }
        //否则，根据退款查询接口响应数据判断:返回refund_status=REFUND_SUCCESS
        else {
            //调用退款查询接口，判断refund_status
            AlipayTradeFastpayRefundQueryRequest refundQueryRequest = new AlipayTradeFastpayRefundQueryRequest();
            AlipayTradeFastpayRefundQueryModel refundQueryModel = new AlipayTradeFastpayRefundQueryModel();
            refundQueryRequest.setBizModel(refundQueryModel);
            refundQueryModel.setOutTradeNo(refundVo.getOrderId());
            refundQueryModel.setOutRequestNo(refundVo.getOrderId());

            //发起查询请求
            AlipayTradeFastpayRefundQueryResponse refundQueryResponse = alipayClient.execute(refundQueryRequest);
            if("10000".equals(refundQueryResponse.getCode()) && "REFUND_SUCCESS".equalsIgnoreCase(refundQueryResponse.getRefundStatus())) {
                log.info("[支付宝退款]支付宝发起退款成功,订单ID：{}", refundVo.getOrderId());
                return true;
            }
            log.info("[支付宝退款]支付宝发起退款失败,订单ID：{}", refundVo.getOrderId());
            return false;
        }
    }
}
