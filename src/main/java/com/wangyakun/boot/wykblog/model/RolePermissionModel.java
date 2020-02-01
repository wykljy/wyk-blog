package com.wangyakun.boot.wykblog.model;



import com.wangyakun.boot.wykblog.model.map.RolePermissionMap;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @ClassName RolePermissionModel
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 16:59
 * @Version 1.0
 **/
@Table(name = "blog_role_permission")
@Data
@IdClass(RolePermissionMap.class)
public class RolePermissionModel {

    @Id
    @Column(name = "p_id")
    private  Integer pid;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

}
