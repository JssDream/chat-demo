package com.jssdream.chatdemo.Enum;

/**
 * 基本枚举类型，表示信息的状态
 *
 * @author Js
 */

public enum EnumBaseStatus implements IntegerValuedEnum {
    NORMAL("正常", 1, "正常状态"), FORBIDDEN("停用", 0, "停用状态");


    // 成员变量
    private String name;

    private int index;

    private String description;

    //构造方法
    private EnumBaseStatus(String name, int index, String description) {
        this.name = name;
        this.index = index;
        this.description = description;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
