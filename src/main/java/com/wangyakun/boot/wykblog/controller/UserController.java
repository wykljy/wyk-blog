package com.wangyakun.boot.wykblog.controller;

import com.wangyakun.boot.wykblog.model.PermissionModel;
import com.wangyakun.boot.wykblog.model.RoleModel;
import com.wangyakun.boot.wykblog.model.UserModel;
import com.wangyakun.boot.wykblog.model.dto.UserDTO;
import com.wangyakun.boot.wykblog.service.UserService;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/21 23:16
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    public final Logger log=Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    @RequiresPermissions("user:query")
    public String  userList(){
        log.info("userList================");
        return "user/user";
    }

    @RequestMapping("/queryUserList")
    @ResponseBody
    //分页page+limit
    public ResponseWrapper queryUserList(@RequestParam("page")int page,@RequestParam("limit")int limit){
        UserDTO dto=new UserDTO();
        dto.setLimit(limit);
        dto.setPage(page);
        ResponseWrapper wrapper=userService.queryUserList(dto);
        return wrapper;
    }



    //需要做异常捕获(权限不足需要拦截)
    @RequestMapping("/preUpdateUser")
    @ResponseBody
    @RequiresPermissions("user:update")
    public ResponseWrapper preUpdateUser()throws  Exception{
        return ResponseWrapperMapper.success();
    }



    @RequestMapping("/updateUser")
    @ResponseBody
    public ResponseWrapper  updateUser (@RequestParam("userId")int userId,
                                       @RequestParam("name")String name){
       UserDTO dto=new UserDTO();
       dto.setUserId(userId);
       dto.setName(name);
       return userService.updateUser(dto);
    }

    @RequestMapping("/preDelUser")
    @RequiresPermissions("user:del")
    @ResponseBody
    public ResponseWrapper preDelUser() throws  Exception{
        return ResponseWrapperMapper.success();
    }


    @RequestMapping("/delUser")
    @ResponseBody
    public ResponseWrapper  delUser(@RequestParam("userId")int userId){
        return userService.delUser(userId);
    }


    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getAllUserList(){
        return userService.getAllUserList();
    }



    //异常捕获，权限不足判断
    @RequestMapping("/preAddUser")
    @ResponseBody
    @RequiresPermissions("user:add")
    public ResponseWrapper preSaveUser()throws  Exception{
        return ResponseWrapperMapper.success();
    }


    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper saveUser(@RequestParam("username")String username,
                                    @RequestParam("password")String password,
                                    @RequestParam("name")String name){
        UserDTO dto=new UserDTO();
        dto.setUsername(username);
        dto.setName(name);
        dto.setPassword(password);
        return userService.saveUser(dto);
    }

     //添加用户页面跳转(判断权限)
    @RequestMapping("/preAddUserView")
    @RequiresPermissions("user:add")
    @ResponseBody
    public ResponseWrapper preAddUserImgView()throws  Exception{
        return ResponseWrapperMapper.success();
    }

    //直接跳转
    @RequestMapping("/addUserView")
    public String addUserImgView(){
        return "user/add";
    }


    //照片存放路径(注意,写入该路径的文件虽然存在，但是在编译之后生成的，所以http对应路径当时访问不到
    // ，后续重新编译启动会访问到(可以选择nginx静态代理))
    private static String UPLOADED_FOLDER = "D://idea project//spring-boot-parent//wyk-blog//src//main//resources//static//assert//image//user//";
    private static String FILE_HEADER="http://localhost:9999/assert/image/user/";
    //照片上传
    @RequestMapping("/uploadImage")
    @ResponseBody
    public ResponseWrapper uploadImage(@RequestParam MultipartFile file )throws  Exception{
        ResponseWrapper wrapper=null;
        if(file.isEmpty()){
            wrapper=ResponseWrapperMapper.errorMsg("上传照片文件为空!!!");
        }else{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            wrapper=ResponseWrapperMapper.success();
            wrapper.setData(FILE_HEADER+file.getOriginalFilename());
        }
       return wrapper;
    }



    @RequestMapping("/addUserImg")
    @ResponseBody
    public ResponseWrapper addUserImg(@RequestParam("username")String username,@RequestParam("password")String password,
                                      @RequestParam("name")String name,@RequestParam("userImage")String userImage){
        log.info("账号:"+username);
        log.info("密码:"+password);
        log.info("别名:"+name);
        log.info("照片:"+userImage);
      return userService.addUserImg(username,name,password,userImage);
    }



    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper saveUser(@RequestBody  UserDTO userDTO){

        return userService.saveUser(userDTO);
    }




    @RequestMapping(value = "/getRoleListByUser",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getRoleListByUser(@RequestParam("userId")int userId){
        UserModel model=new UserModel();
        model.setId(userId);
        List<RoleModel> list=userService.getRoleListByUser(model);
        return ResponseWrapperMapper.successByData(list);
    }

    @RequestMapping(value = "/getPermissionListByRoleId",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getPermissionListByRoleId(@RequestParam("roleId")int roleId){
        List<PermissionModel> list=userService.getPermissionListByRoleId(roleId);
        return ResponseWrapperMapper.successByData(list);
    }



}
