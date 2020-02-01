package com.wangyakun.boot.wykblog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName PermissionModel
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 16:39
 * @Version 1.0
 **/
@Table(name = "blog_permission")
@Data
public class PermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    @Column(name = "id")
    private Integer id;

    @Column(name = "p_name")
    private String pName;

    @Column(name = "p_url")
    private String pUrl;

    @Column(name = "p_type")
    private String pType;

    @Column(name = "p_per")
    private String per;

    private int pid;

    @Column(name = "create_time")
    private Date createTime;

}
