package org.uts.global.errorCode;

/**
 * @Description 业务错误码
 * @Author codBoy
 * @Date 2024/8/6 21:35
 */
public enum BusinessErrorCode {

    //******************************* Redis操作 ******************************
    REDIS_TRY_LOCK_OVER_COUNT("010001", "Redis加锁操作过于频繁"),


    //******************************* AliPay业务 ******************************
    SEND_PAY_FAILED("020001", "发起支付失败"),

    SEND_REFUND_FAILED("020002", "发起支付失败"),

    SIGNATURE_PAY_FAILED("020003", "消息验签失败"),
    ;


    //错误码
    private String code;

    //错误消息
    private String errorMsg;

    BusinessErrorCode(String code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
