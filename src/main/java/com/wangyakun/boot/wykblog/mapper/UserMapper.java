package com.wangyakun.boot.wykblog.mapper;
import com.wangyakun.boot.wykblog.base.MyMapper;
import com.wangyakun.boot.wykblog.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper extends MyMapper<UserModel> {
}
