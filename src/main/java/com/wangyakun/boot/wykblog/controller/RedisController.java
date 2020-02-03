package com.wangyakun.boot.wykblog.controller;
import com.wangyakun.boot.wykblog.config.redis.RedisUtil;
import com.wangyakun.boot.wykblog.model.UserModel;
import com.wangyakun.boot.wykblog.model.dto.UserDTO;
import com.wangyakun.boot.wykblog.service.UserService;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
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
    @Autowired
    private RedisUtil redisUtil;

    //redis 中存储的过期时间为60秒
    private static int ExpireTime = 60;

    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper saveUser(@RequestBody UserDTO userDTO){
        ResponseWrapper wrapper=userService.saveUser(userDTO);
        if(wrapper.getCode()==0){
           redisUtil.set(userDTO.getUsername(),userDTO,ExpireTime);
        }
        return wrapper;
    }

    @RequestMapping(value = "/findUserBuUsername",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper findUserBuUsername(@RequestParam("username")String username){
        ResponseWrapper wrapper=null;
        Object object=redisUtil.get(username);
        if(object !=null){
            log.info("从redis 缓存中获取数据=====================");
            wrapper= ResponseWrapperMapper.success();
            wrapper.setData(object);
        }else{
            log.info("从数据库获取数据===========================");
            UserModel model =userService.getUserByUsername(username);
            wrapper= ResponseWrapperMapper.success();
            wrapper.setData(model);
            redisUtil.set(model.getUsername(),model,ExpireTime);
        }
        return  wrapper;
    }


}
