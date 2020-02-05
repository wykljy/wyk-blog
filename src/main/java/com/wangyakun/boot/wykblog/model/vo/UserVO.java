package com.wangyakun.boot.wykblog.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserVO
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/21 23:02
 * @Version 1.0
 **/
@Data
public class UserVO implements Serializable {

     private static final long serialVersionUID=1L;

     private int userId;

     private String name;

     private String username;

     private String password;

     private String createTime;

     private String image;
}
