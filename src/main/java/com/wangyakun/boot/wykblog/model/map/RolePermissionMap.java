package com.wangyakun.boot.wykblog.model.map;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RolePermissionMap
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 17:01
 * @Version 1.0
 **/
@Data
public class RolePermissionMap implements Serializable {
    private int pid;
    private int roleId;
}
