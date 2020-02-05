package com.wangyakun.boot.wykblog.service;

import com.wangyakun.boot.wykblog.model.dto.RoleDTO;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;

public interface RoleService {
    ResponseWrapper queryRoleList(RoleDTO roleDTO);
}
