package com.jssdream.chatdemo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 状态码
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeMsg {
    private int code;
    private String msg;

    //通用的错误码    5001XX
    public static CodeMsg SUCCESS = new CodeMsg(100, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg PUBLIC_ERROR = new CodeMsg(500200, "通用异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg NULL_ERROR = new CodeMsg(500202, "数据不存在");
    public static CodeMsg NO_DATA_ERROR = new CodeMsg(500203, "%s");

    //用户模块  5002XX
    public static CodeMsg LOGIN_FAIL= new CodeMsg(500200, "登录失败，用户名或密码错误");
    public static CodeMsg LOGIN_INVALID= new CodeMsg(500201, "登录失效，请重新登录");

    //订单模块

}
