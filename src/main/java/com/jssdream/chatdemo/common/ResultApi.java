package com.jssdream.chatdemo.common;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果信息
 */
@Data
@Builder
@AllArgsConstructor
public class ResultApi implements Serializable {
    private int status;
    private String msg = "";
    private Object data = new JSONObject();

    public ResultApi() {
        this.status = CodeMsg.SUCCESS.getCode();
    }

    public ResultApi(String msg) {
        this.status = 0;
        this.msg = msg;
    }

    public ResultApi(Object data) {
        this.status = CodeMsg.SUCCESS.getCode();
        this.data = data;
    }

    public ResultApi(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultApi(String msg, Object data) {
        if (StrUtil.isBlank(msg)) {
            this.status = CodeMsg.SUCCESS.getCode();
        }
        this.msg = msg;
        this.data = data == null ? new JSONObject() : data;
    }


}
