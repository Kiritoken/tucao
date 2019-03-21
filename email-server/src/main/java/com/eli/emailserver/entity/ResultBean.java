package com.eli.emailserver.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -5125405247695633324L;

    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
    public static final int UNAUTH = -2;
    public static final int UNAUTHORIZE = -3;
    public static final int PASSWORDERROR = -4;
    public static final int ERROR = -5;
    public static final int REDIRECT = -6;

    private int code=SUCCESS;
    private  String msg;
    private T data;

    public ResultBean( ){
        super();
    }

    public ResultBean(@NotNull Throwable e){
        super();
        this.msg=e.getMessage();
        this.code=FAIL;
    }

    public ResultBean(String msg,int code,T data){
        super();
        this.msg=msg;
        this.code=code;
        this.data=data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
