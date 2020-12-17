package com.house.property.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {

    public final static Integer STATUS_OK = 0;
    public final static Integer STATUS_ERROR = 1;
    private static final long serialVersionUID = -8842655030457075677L;

    private Integer code;
    /**
     * 回传实体
     */
    private Object data;

    /**
     * 回传消息
     */
    private String message = "";



    public Response(){}
    public Response(Object data) {
        this.data = data;
        this.code = STATUS_OK;
    }
    public Response(Integer code ,Object data,String message) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
    public Response(String message) {
        this.message = message;
        this.code = STATUS_OK;
    }
    public Response(Integer code ,String message) {
        this.message = message;
        this.code = code;
    }

}
