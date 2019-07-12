package com.xi.demo.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

/**
 * 异常处理拦截器
 * Created by xxb on 2018/10/22.
 */
@CrossOrigin
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpStatusCodeException.class)
    public JSONObject httpStatusCodeExceptionHandler(HttpStatusCodeException ex) {
        return exceptionFormat("HTTPCode异常", ex);
    }


    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    public JSONObject runtimeExceptionHandler(RuntimeException ex) {
        return exceptionFormat("运行时异常", ex);
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public JSONObject nullPointerExceptionHandler(NullPointerException ex) {
        return exceptionFormat("空指针异常", ex);
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public JSONObject classCastExceptionHandler(ClassCastException ex) {
        return exceptionFormat("类型转换异常", ex);
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    public JSONObject iOExceptionHandler(IOException ex) {
        return exceptionFormat("IO异常", ex);
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    public JSONObject noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return exceptionFormat("未知方法异常", ex);
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public JSONObject indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return exceptionFormat("数组越界异常", ex);
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public JSONObject requestNotReadable(HttpMessageNotReadableException ex) {
        log.error("400..requestNotReadable");
        return exceptionFormat("400..requestNotReadable", ex);
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    public JSONObject requestTypeMismatch(TypeMismatchException ex) {
        log.error("400..TypeMismatchException");
        return exceptionFormat("400..TypeMismatchException", ex);
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public JSONObject requestMissingServletRequest(MissingServletRequestParameterException ex) {
        log.error("400..MissingServletRequest");
        return exceptionFormat("400..MissingServletRequest", ex);
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public JSONObject request405(HttpRequestMethodNotSupportedException ex) {
        return exceptionFormat("405错误", ex);
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public JSONObject request406(HttpMediaTypeNotAcceptableException ex) {
        log.error("406...");
        return exceptionFormat("406错误", ex);
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public JSONObject server500(RuntimeException ex) {
        log.error("500...");
        return exceptionFormat("500错误", ex);
    }

    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
    public JSONObject requestStackOverflow(StackOverflowError ex) {
        return exceptionFormat("栈溢出", ex);
    }

    //其他错误
    @ExceptionHandler({Exception.class})
    public JSONObject exception(Exception ex) {
        return exceptionFormat("其他错误", ex);
    }

//    自定义异常捕获
    @ExceptionHandler({MyException.class})
    public JSONObject myException(MyException ex) {
        log.error("1111111111111111111");
        return exceptionFormat("自定义异常", ex);
    }

    private <T extends Throwable> JSONObject exceptionFormat(String message,T ex) {
//        ex.printStackTrace();
        JSONObject result =new JSONObject();
        result.put("code",0);
        result.put("message",message);
        result.put("errorMags",ex);
        log.error(result.toJSONString());
        return result;
    }
}
