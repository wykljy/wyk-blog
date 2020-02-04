package com.wangyakun.boot.wykblog.controller;
import cn.hutool.core.date.DateUtil;
import com.wangyakun.boot.wykblog.config.redis.RedisUtil;
import com.wangyakun.boot.wykblog.model.dto.UserDTO;
import com.wangyakun.boot.wykblog.model.vo.UserVO;
import com.wangyakun.boot.wykblog.service.UserService;
import com.wangyakun.boot.wykblog.util.JsonUtils;
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

    @Autowired
    private RedisUtil redisUtil;
    //redis 会自动判断key 值是否存在，存在即更新
    @RequestMapping(value = "/setData",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper setData(@RequestParam("key")String key)throws  Exception{
        UserVO vo=new UserVO();
        vo.setUsername("zs");
        vo.setPassword("123456");
        vo.setName("张三");
        vo.setCreateTime(DateUtil.now());
        vo.setUserId(1);
        vo.setImage("www.baidu.com");
        redisUtil.set(key, JsonUtils.object2Json(vo));
        return ResponseWrapperMapper.success();
    }


    @RequestMapping(value = "/getData",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getData(@RequestParam("key")String key){
        ResponseWrapper wrapper=null;
        boolean b=redisUtil.hasKey(key);
        if(b){
            Object object=redisUtil.get(key);
            UserVO vo=JsonUtils.json2Object(object.toString(),UserVO.class);
            wrapper=ResponseWrapperMapper.success();
            wrapper.setData(vo);
        }else{
            wrapper=ResponseWrapperMapper.errorMsg("该索引不存在数据");
        }
        return wrapper;
    }

    @RequestMapping(value = "/delData",method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper delData(@RequestParam("key")String key){
        ResponseWrapper wrapper=null;
        boolean b=redisUtil.hasKey(key);
        if(b){
           redisUtil.del(key);
           wrapper=ResponseWrapperMapper.success();
       }else{
            wrapper=ResponseWrapperMapper.errorMsg("该索引不存在数据");
        }
        return wrapper;
    }








}
