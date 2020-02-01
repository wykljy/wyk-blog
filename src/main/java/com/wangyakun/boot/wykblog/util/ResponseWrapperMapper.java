package com.wangyakun.boot.wykblog.util;

import com.wangyakun.boot.wykblog.constant.RespEnum;

/**
 * @ClassName ResponseWrapperMapper
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/12 23:07
 * @Version 1.0
 **/
public class ResponseWrapperMapper {
    public static ResponseWrapper success(){
        return new ResponseWrapper();
    }

    public static  ResponseWrapper successByData(Object data){
        return new ResponseWrapper(ResponseWrapper.SUCCESS_CODE,ResponseWrapper.SUCCESS_MESSAGE,data);
    }

    public static  ResponseWrapper successByData(Object data,int count){
        return new ResponseWrapper(ResponseWrapper.SUCCESS_CODE,ResponseWrapper.SUCCESS_MESSAGE,data,count);
    }


    public static ResponseWrapper error(){
        return new ResponseWrapper(ResponseWrapper.ERROR_CODE,ResponseWrapper.ERROR_MESSAGE);
    }

    public static ResponseWrapper errorEnum(RespEnum respEnum){
        return new ResponseWrapper(respEnum.getStatus(),respEnum.getMsg());
    }

    public static ResponseWrapper errorMsg(String msg){
        return new ResponseWrapper(ResponseWrapper.ERROR_CODE,msg);
    }

}
