package com.viewpoint.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    UP(1,"已发布"),
    DOWN(0,"未发布"),
    ADMIN(2,"管理员"),
    REGISTERED(1,"已注册"),
    UNREGISTERED(0,"未注册")
    ;
    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
