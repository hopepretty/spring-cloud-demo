package com.pc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {

    /**
     * 状态码
     */
    private Integer code = 200;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 成功失败
     */
    private boolean success;
    /**
     * 数据
     */
    private T data;

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
