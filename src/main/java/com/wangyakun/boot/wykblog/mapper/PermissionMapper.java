package com.wangyakun.boot.wykblog.mapper;

import com.wangyakun.boot.wykblog.base.MyMapper;
import com.wangyakun.boot.wykblog.model.PermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper
public interface PermissionMapper extends MyMapper<PermissionModel> {

    List<PermissionModel> getPermissionListByRoleId(@Param("roleId")int roleId);
}
