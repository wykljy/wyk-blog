package com.wangyakun.boot.wykblog.model;

import com.wangyakun.boot.wykblog.model.map.UserRoleMap;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @ClassName UserRoleModel
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 16:47
 * @Version 1.0
 **/
@Table(name = "blog_user_role")
@Data
@IdClass(UserRoleMap.class)
public class UserRoleModel {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "role_id")
    private Integer roleId;
}
