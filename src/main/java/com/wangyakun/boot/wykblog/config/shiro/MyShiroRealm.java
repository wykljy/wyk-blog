package com.wangyakun.boot.wykblog.config.shiro;
import com.wangyakun.boot.wykblog.model.PermissionModel;
import com.wangyakun.boot.wykblog.model.RoleModel;
import com.wangyakun.boot.wykblog.model.UserModel;
import com.wangyakun.boot.wykblog.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName MyShiroRealm
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/21 23:42
 * @Version 1.0
 **/
public class MyShiroRealm extends AuthorizingRealm {
    public  final Logger log= Logger.getLogger(MyShiroRealm.class);

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限配置(获取对应的角色和权限配置信息)=====================");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserModel userModel=(UserModel) principals.getPrimaryPrincipal();
        log.info("用户配置:"+userModel.getUsername());
        List<RoleModel> roleList=userService.getRoleListByUser(userModel);
        if(!StringUtils.isEmpty(roleList)){
            for(RoleModel roleModel:roleList){
                log.info("角色配置:"+roleModel.getRoleName());
                authorizationInfo.addRole(roleModel.getRoleName());
                List<PermissionModel> perList=userService.getPermissionListByRoleId(roleModel.getId());
                for(PermissionModel permissionModel:perList){
                    log.info("权限配置:"+permissionModel.getPer());
                    authorizationInfo.addStringPermission(permissionModel.getPer());
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("身份认证(校验用户名和密码是否正常)=====================");
        String username=(String) token.getPrincipal();
        UserModel userModel=userService.getUserByUsername(username);
        if(StringUtils.isEmpty(userModel)){
            log.info("用户信息不存在============");
            return null;
        }else {
            log.info("user info:"+userModel.toString());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    userModel,
                    userModel.getPassword(),
                    ByteSource.Util.bytes(userModel.getCredentialsSalt()),
                    getName()
            );
            return authenticationInfo;
        }
    }
}
