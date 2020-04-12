package com.pc.payment.exception;

import com.pc.entities.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pc
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * 异常处理
     * @param exception
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public JsonResult<String> handleException(Exception exception) {
        exception.printStackTrace();
        JsonResult<String> jsonResult = new JsonResult<>();
        System.out.println("异常以处理");
        jsonResult.setSuccess(false);
        jsonResult.setMessage(exception.getMessage());
        return jsonResult;
    }

}
