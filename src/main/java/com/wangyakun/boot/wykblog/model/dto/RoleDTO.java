package com.wangyakun.boot.wykblog.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RoleDTO
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/2/5 13:08
 * @Version 1.0
 **/
@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private int page;

    private int limit;
}
