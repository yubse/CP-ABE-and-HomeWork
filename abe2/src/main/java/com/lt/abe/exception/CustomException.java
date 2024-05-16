package com.lt.abe.exception;

/**
 * @Author 物联网2班刘婷
 * @Description CustomException
 * @Date 2024/3/2
 */

public class CustomException extends RuntimeException{
    private String msg;
    public CustomException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
}
