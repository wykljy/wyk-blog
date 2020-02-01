package com.wangyakun.boot.wykblog.mapper;

import com.wangyakun.boot.wykblog.base.MyMapper;
import com.wangyakun.boot.wykblog.model.RolePermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RolePermissionMapper extends MyMapper<RolePermissionModel> {
}
