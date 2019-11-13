package com.jssdream.chatdemo.common;


import com.jssdream.chatdemo.Enum.EnumBaseStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseBean implements Serializable {
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 最后修改时间
     */
    private Date updateTime;
    /**
     * 修改次数
     */
    private int modifiedCount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态
     */
    private EnumBaseStatus enumDeleted;
    private Integer deleted;
}
