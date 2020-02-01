package com.wangyakun.boot.wykblog.mapper;

import com.wangyakun.boot.wykblog.base.MyMapper;
import com.wangyakun.boot.wykblog.model.UserRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserRoleMapper extends MyMapper<UserRoleModel> {
}
