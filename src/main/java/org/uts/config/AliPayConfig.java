package org.uts.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * @ClassName AliPayConfig
 * @Description TODO
 * @Author codBoy
 * @Date 2024/8/31 19:28
 */
public class AliPayConfig {
    // 商户appid
    public static String APP_ID = "2021000122658017";

    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCaE81wQSbjX0pPxH7rGk25yQXMgIXuxtfupnFd+9OB9hqgzjI6Mm1ivW4kmNwz3zDHrgDJMYP0+jRYM38aGWYwzj3pK1iGhOpl39teOZmG2oT9+c+6gfBRTqP84bvMj+sVarBsKArTTIe8F9DeiU1CsRuvFxbp4hdpChrKKtzzDyegFMIDl2LNfhLdnYGpLI6FfULOaCfpruHBax5y6VxoJvyNt0GlyLIvD+URPafax3xB9T7FxmhV3F3Rvp0PjrlV7s+6UU4Fh3K537cwJKDJquXA2wQJAhRpNbf+W+lgVA5mVOYl05xgArYh4F4UtU9nNrXtPuJN/y86Tmm5bQTAgMBAAECggEAO1zSWezgdRLmW8VLIV6tkoGLi0eHblgNMPmXkC11cPbfTDUpv0NAtvRnwLpaq/q3l3a/Wyb9Ri03uUAw5Gp9fRm5L/n1t0eZlcTSC/pSHz7xfd+Gk7wjBZbG/spjrTUZiRCCJ6OIdc2xOCZBp0zN7SRWoPX7sPlFeSMGBym/SnmSf4pJX1U8eVMg/CBTg8Z0s7+ZTpQxd8YZzasqGBWfWZ69LkWF8pycvqUJ3xs17BXj0XHH/7sR4yvaLnKv9MnIcoxzGl0UWYzx2L7otDuTAY0ey5nvk88rZYskwYb2dExE6JvqvfL7jMWMUGMAbAqOUbdpyqhQG5K2mbCYySDyOQKBgQDElMtiRKkwb2MXTqG3WxI1H3xBPY9mB1I3BV94wbS9AO1RBniSknaJWxXPxR6WX0DgdSgaqI2Dg3M1mmqKjsqweHvRWiOoZ2YBdwosclFriyWXMCwPZlfZBB0JqjVEyhpGUjksl5uwqZ4w/Y2vQponWgHKhJZgx1+4a2DjUcUvhwKBgQCp0xKhM9qaBhdad9RyknbT5DPEWKGwttTB2JxCMXrs8Xe1CxNRr7N80RTLOyI/i6eRZ3i1OIk/Fc1CWyZ4q7G1crHwGXDvzDBcQmvXcoYWY5WbZLcn0rKCI4ESSmxkOC8JYaQAB5WIAQpEaoJdgaoSNVoBjlY6ryXqb8MYtshCFQKBgQCnIC0tNVx1vi0LcqFQBhAbs9HqkVCpmE3QUzQQsPZcvT4FZa/6RKScMCECC/7uLrs0WMYoq+XrykSXbNlpRO6TS0tQ+AZatNVnJ3IxyVrpSFpECANdcE/9Q18AiYFSI1RlNqA/BgGqFvpTgi0DtGbkT8Q4DbLfQoFhg4DSogE3XwKBgEHqD/gLpVRU+ul9SzMxD/gMQd96uz9Yp1mZ1oSGqfyfeGQHqXbyKEtQ564+BHv/m4l/TMWh7PtUXffcYyFBGtefbdsQNNqOOVMF4M+dvtefzWij/cbyK6EBYM6Vnl6DxBXzFPGCABLqvG5znKfodPdeR9IEygk7BA9rU6EbCL1FAoGAO/+xEAjTg8psDtDx2dyjOYlq3mjjLpRTSEl2ZsRvkvTi+z71z9/daed6mURc/6m/Av6ti/BSD/EJmcGEDh/8WG0FbRthXGIZ6F80QxEGVc7eHfq4JpPT56bplh2tnzTYDSL5bBSn/mk+ox0VTU+1vusmHzHsQlec9t8j7CFLzpw=";

    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlz3d9RLa7jcWgUbLQQkZktbNwBdac1c+VWcX180Q2macUG5Swd9eWab5GPGWkCQebNozjGrinQJ7ymlIZImv9k+KficAPCEOgVB5ycRyAIEyuuj0OIcaLxl5oI7dFeweIqV897LKOfnGPp49B3H0BeKqxRaDERIhvDpbrEQh1LCuXd+RX8zOr1SeBKi7XZ8vJfpyjp+qhZ/piYVlVt2df4pmI/0BNWSjpGPs81VklKo8dSczgBvhhiSzT9oJq/BtJOycQYGT1qhrm6MZj0YDbM28uZDeYNSnXCLJo1Qjko64lKoOJ+P9eEdwIvdWDYN76gVzT6w4/Vct7OkIEYsZBQIDAQAB";


    // 请求网关地址
    public static String GATE_WAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String RETURN_URL = "http://jv4r5c.natappfree.cc/uts-apigw/uts-pay/alipay/returnSync";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL = "http://jv4r5c.natappfree.cc/uts-apigw/uts-pay/alipay/notifyAsc";



    // 编码
    public static String CHARSET = "UTF-8";

    // 返回格式
    public static String FORMAT = "json";

    // 日志记录目录
    public static String log_path = "/log/uts-pay/alipay";

    // RSA2
    public static String SIGN_TYPE = "RSA2";


    private static volatile AlipayClient alipayClient;

    private static final Object LOCK = new Object();

    public static AlipayClient getAlipayClient() {
        if(alipayClient == null) {
            synchronized (LOCK) {
                if(alipayClient == null) {
                    alipayClient = new DefaultAlipayClient(
                            GATE_WAY_URL,
                            APP_ID,
                            RSA_PRIVATE_KEY,
                            FORMAT,
                            CHARSET,
                            ALIPAY_PUBLIC_KEY,
                            SIGN_TYPE);
                }
            }
        }
        return alipayClient;
    }
}
