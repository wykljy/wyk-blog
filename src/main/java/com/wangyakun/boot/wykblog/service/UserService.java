package com.wangyakun.boot.wykblog.service;

import com.wangyakun.boot.wykblog.model.PermissionModel;
import com.wangyakun.boot.wykblog.model.RoleModel;
import com.wangyakun.boot.wykblog.model.UserModel;
import com.wangyakun.boot.wykblog.model.dto.UserDTO;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;

import java.util.List;

public interface UserService {
    ResponseWrapper getAllUserList();

    ResponseWrapper queryUserList(UserDTO userDTO);

    UserModel getUserByUsername(String username);

    ResponseWrapper getUserByRedis(String username);

    ResponseWrapper updateUser(UserDTO userDTO);

    List<RoleModel> getRoleListByUser(UserModel userModel);

    List<PermissionModel> getPermissionListByRoleId(int roleId);

    ResponseWrapper saveUser(UserDTO userDTO);

    ResponseWrapper addUserImg(String username,String name,String pwd,String img,int roleId);

    ResponseWrapper delUser(int userId);

    ResponseWrapper getIndexPageData();


}
