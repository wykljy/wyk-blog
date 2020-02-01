package com.wangyakun.boot.wykblog.model.map;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserRoleMap
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 16:50
 * @Version 1.0
 **/
@Data
public class UserRoleMap implements Serializable {

    private static final long serialVersionUID=1L;

    private int userId;
    private int roleId;

}
