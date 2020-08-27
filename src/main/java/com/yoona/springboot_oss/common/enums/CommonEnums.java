package com.yoona.springboot_oss.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 11:05 上午
 * @Msg: 通用状态枚举
 */
@Getter
@AllArgsConstructor
public enum CommonEnums {

    /**
     * 状态枚举
     */
    NORMAL(1, "操作成功"),
    UN_KNOW_ERROR(-1, "未知错误"),
    UPLOADED_FORMAT_IS_ILLEGAL(100,"上传格式不合法"),
    UPLOAD_SUCCESSFULLY(200,"上传成功"),
    UPLOAD_FAILED(201,"上传失败")
    ;

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
