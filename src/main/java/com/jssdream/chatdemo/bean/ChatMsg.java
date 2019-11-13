package com.jssdream.chatdemo.bean;

import com.jssdream.chatdemo.Enum.EnumMsgType;
import com.jssdream.chatdemo.common.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ChatMsg
 * @Description: 消息实体
 * @Author Js
 * @Date 2019/11/11
 * @Version V1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg extends BaseBean {
    private String content;
    private String sender;
    private EnumMsgType enumMsgType;
    private Integer msgType;
}
