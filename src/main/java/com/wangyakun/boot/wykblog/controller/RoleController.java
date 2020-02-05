package com.wangyakun.boot.wykblog.controller;
import com.wangyakun.boot.wykblog.model.dto.RoleDTO;
import com.wangyakun.boot.wykblog.service.RoleService;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/2/5 13:06
 * @Version 1.0
 **/
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //页面跳转需要判断权限
    @RequestMapping("/roleList")
    @RequiresPermissions("role:query")
    public String roleList()throws  Exception{
        return "role/role";
    }


    //获取角色列表
    @RequestMapping("/queryRoleList")
    @ResponseBody
    public ResponseWrapper queryRoleList(@RequestParam("page")int page,@RequestParam("limit")int limit){
        RoleDTO roleDTO=new RoleDTO();
        roleDTO.setLimit(limit);
        roleDTO.setPage(page);
       return roleService.queryRoleList(roleDTO);
    }

    @RequestMapping("/preUpdateRole")
    @ResponseBody
    @RequiresPermissions("role:update")
    public ResponseWrapper preUpdateRole()throws  Exception{
        return ResponseWrapperMapper.success();
    }


    @RequestMapping("/preDelRole")
    @RequiresPermissions("role:del")
    @ResponseBody
    public ResponseWrapper preDelRole() throws  Exception{
        return ResponseWrapperMapper.success();
    }


    //异常捕获，权限不足判断
    @RequestMapping("/preAddRole")
    @ResponseBody
    @RequiresPermissions("role:add")
    public ResponseWrapper preAddRole()throws  Exception{
        return ResponseWrapperMapper.success();
    }


}
