package com.wangyakun.boot.wykblog.constant;
public enum RespEnum {
    SYSTEM_REQUEST_DATA_SUCCESS(1,"SUCCESS"),
    REGISTER_USER_EXISTS(201,"注册用户已经存在"),
    LOGIN_ACCOUNT_NOT_EXISTS_ERROR(101,"未知账户"),
    LOGIN_PASSWORD_ERROR(102,"密码错误"),
    LOGIN_ACCOUNT_IS_LOCK(103,"账户已锁定"),
    LOGIN_USERNAME_OR_PASSWORD_NOT_ONE(104,"用户名或密码错误次数过多"),
    LOGIN_USERNAME_OR_PASSWORD_ERROR(105,"用户名或者密码错误"),
    SYSTEM_HAS_NO_DATA(1000,"系统暂无数据");



   private int status;


   private String msg;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    RespEnum(int status,String msg){
        this.status=status;
        this.msg=msg;
    }
}
