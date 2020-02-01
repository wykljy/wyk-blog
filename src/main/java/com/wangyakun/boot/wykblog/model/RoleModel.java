package com.wangyakun.boot.wykblog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName RoleModel
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 16:27
 * @Version 1.0
 **/
@Table(name = "blog_role")
@Data
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_des")
    private String roleDes;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name ="role_flag")
    private Boolean roleFlag;

}
