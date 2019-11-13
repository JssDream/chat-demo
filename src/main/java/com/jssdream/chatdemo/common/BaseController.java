package com.jssdream.chatdemo.common;

/**
 * 基类
 */
public class BaseController {

    public ResultApi success(String msg){
        return new ResultApi(msg);
    }

    public ResultApi success(){
        return new ResultApi();
    }

    public ResultApi success(Object data){
        return new ResultApi(data);
    }

    public ResultApi success(CodeMsg codeMsg){
        return new ResultApi(codeMsg.getCode() , codeMsg.getMsg());
    }

}
