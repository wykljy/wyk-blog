package com.wangyakun.boot.wykblog.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UserModel
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/12 20:28
 * @Version 1.0
 **/
@Table(name = "blog_user")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    private String username;


    private String password;

    private String name;

    private Date createTime;

    private String salt;

    private String image;

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }

}
