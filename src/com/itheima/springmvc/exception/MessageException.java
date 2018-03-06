package com.itheima.springmvc.exception;

/**
 * 文件说明
 * Author JiJG(jijg)<jijg@lizi.com>
 * date:2018-03-05 13:56
 */
public class MessageException extends Exception {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageException(String msg) {
        this.msg = msg;
    }

}
