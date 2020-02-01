package com.wangyakun.boot.wykblog.mapper;
import com.wangyakun.boot.wykblog.base.MyMapper;
import com.wangyakun.boot.wykblog.model.RoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface RoleMapper extends MyMapper<RoleModel> {

    List<RoleModel> getRoleListByUserId(@Param("userId")int userId);
}
