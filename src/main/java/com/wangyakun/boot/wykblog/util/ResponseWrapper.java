package com.wangyakun.boot.wykblog.util;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ResponseWrapper
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/12 23:04
 * @Version 1.0
 **/
@Data
public class ResponseWrapper<T> implements Serializable {
    //序列化标识
    private static final long serialVersionUID = 4893280118017319089L;

    //成功状态码
    public static final int  SUCCESS_CODE=0;

    //成功提示信息
    public static final String SUCCESS_MESSAGE="操作成功";

    //错误码
    public static final int ERROR_CODE=1000;

    //错误提示信息
    public static final String ERROR_MESSAGE="系统异常";

    private int code;

    private String msg;

    private String time;

    private int count;

    private T  data;


    //设置状态,并返回自身引用
    private ResponseWrapper<T> code(int code){
        this.setCode(code);
        return this;
    }

    //设置消息,并返回自身引用
    private ResponseWrapper<T> msg(String  msg){
        this.setMsg(msg);
        return this;
    }

    //设置时间,并返回自身引用
    private ResponseWrapper<T> time(String time){
        this.setTime(time);
        return this;
    }

    //设置返回结果，并返回自身引用
    public  ResponseWrapper<T> data(T data){
        this.setData(data);
        return  this;
    }

    public ResponseWrapper<T> count(int count){
        this.setCount(count);
        return this;
    }

    ResponseWrapper(){
        this(SUCCESS_CODE,SUCCESS_MESSAGE);
    }



    ResponseWrapper(int status,String msg){
        this(status,msg,DateUtil.now(),null);
    }


    ResponseWrapper(int status,String msg,T data){
        super();
        this.code(status).msg(msg).time(DateUtil.now()).data(data);
    }

    ResponseWrapper(int status,String msg,String time,T data){
        super();
        this.code(status).msg(msg).time(time).data(data);
    }

    ResponseWrapper(int status,String msg,T data,int count){
        super();
        this.code(status).msg(msg).time(DateUtil.now()).data(data).count(count);
    }


    ResponseWrapper(int status,String msg,String time,T data,int count){
        super();
        this.code(status).msg(msg).time(time).data(data).count(count);
    }
}
