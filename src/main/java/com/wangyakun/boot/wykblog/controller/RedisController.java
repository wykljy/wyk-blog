package com.wangyakun.boot.wykblog.controller;
import com.wangyakun.boot.wykblog.model.dto.UserDTO;
import com.wangyakun.boot.wykblog.service.UserService;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @ClassName RedisController
 * @Description redis
 * @Author wangyakun
 * @Date 2020/2/3 22:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {

    public final Logger log= Logger.getLogger(RedisController.class);

    @Autowired
    private UserService userService;




    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper saveUser(@RequestBody UserDTO userDTO){
        ResponseWrapper wrapper=userService.saveUser(userDTO);
        return wrapper;
    }

    @RequestMapping(value = "/findUserBuUsername",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper findUserBuUsername(@RequestParam("username")String username){
        return userService.getUserByRedis(username);

    }


}
