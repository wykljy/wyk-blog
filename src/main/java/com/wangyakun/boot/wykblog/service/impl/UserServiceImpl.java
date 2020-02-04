package com.wangyakun.boot.wykblog.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangyakun.boot.wykblog.config.redis.RedisUtil;
import com.wangyakun.boot.wykblog.constant.RespEnum;
import com.wangyakun.boot.wykblog.mapper.PermissionMapper;
import com.wangyakun.boot.wykblog.mapper.RoleMapper;
import com.wangyakun.boot.wykblog.mapper.UserMapper;
import com.wangyakun.boot.wykblog.model.PermissionModel;
import com.wangyakun.boot.wykblog.model.RoleModel;
import com.wangyakun.boot.wykblog.model.UserModel;
import com.wangyakun.boot.wykblog.model.dto.UserDTO;
import com.wangyakun.boot.wykblog.model.vo.UserVO;
import com.wangyakun.boot.wykblog.service.UserService;
import com.wangyakun.boot.wykblog.util.JsonUtils;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/12 20:51
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    public final Logger log=Logger.getLogger(UserServiceImpl.class);

    //redis 缓存
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseWrapper getAllUserList() {
        ResponseWrapper wrapper;
        //增加redis 缓存实现
        String key="allUser";
        ValueOperations<String,String> ops=redisTemplate.opsForValue();
        boolean flag=redisTemplate.hasKey(key);
        if(flag){
            log.info("缓存中获取用户数据==============");
            Object object=ops.get(key);
            //List<UserVO> vos= JsonUtils.json2Object(object.toString(),null);
            List<UserVO> vos=new ArrayList<>();
            JSONArray array= JSONUtil.parseArray(object.toString());
            for(int i=0;i<array.size();i++){
                JSONObject temp=array.getJSONObject(i);
                UserVO vo=new UserVO();
                vo.setUserId(temp.getInt("userId"));
                vo.setUsername(temp.getStr("username"));
                vo.setPassword(temp.getStr("password"));
                vo.setImage(temp.getStr("image"));
                vo.setName(temp.getStr("name"));
                vo.setCreateTime(temp.getStr("createTime"));
                vos.add(vo);
            }
            wrapper = ResponseWrapperMapper.successByData(vos);
        }else {
            log.info("数据库获取用户数据==============");
            List<UserModel> list = userMapper.selectAll();
            if (CollUtil.isNotEmpty(list)) {
                List<UserVO> vos = new ArrayList<>();
                for (UserModel model : list) {
                    UserVO vo = new UserVO();
                    vo.setName(model.getName());
                    vo.setUsername(model.getUsername());
                    vo.setPassword(model.getPassword());
                    vo.setCreateTime(DateUtil.format(model.getCreateTime(),
                            "yyyy-MM-dd HH:mm:ss"));
                    vo.setImage(model.getImage());
                    vos.add(vo);
                }
                wrapper = ResponseWrapperMapper.successByData(vos);
                ops.set(key,JsonUtils.object2Json(vos));

            } else {
                wrapper = ResponseWrapperMapper.errorEnum(RespEnum.SYSTEM_HAS_NO_DATA);
            }
        }
           return wrapper;
    }

    //需要考虑分页实现
    @Override
    public ResponseWrapper queryUserList(UserDTO userDTO) {
        ResponseWrapper wrapper;
        PageHelper.startPage(userDTO.getPage(),userDTO.getLimit());
        Example example=new Example(UserModel.class);
        List<UserModel> list=userMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(list)) {
            PageInfo<UserModel> page = new PageInfo<>(list);
            List<UserVO> vos=new ArrayList<>();
            for(UserModel model:page.getList()){
                UserVO vo=new UserVO();
                vo.setUserId(model.getId());
                vo.setName(model.getName());
                vo.setUsername(model.getUsername());
                vo.setPassword(model.getPassword());
                vo.setImage(model.getImage());
                vo.setCreateTime(DateUtil.format(model.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                vos.add(vo);
            }
            wrapper= ResponseWrapperMapper.successByData(vos,(int)page.getTotal());
        }else{
            wrapper=ResponseWrapperMapper.errorEnum(RespEnum.SYSTEM_HAS_NO_DATA);
        }
          return wrapper;
        /*
        List<UserModel> list=userMapper.selectAll();
        if(CollUtil.isNotEmpty(list)){
            List<UserVO> vos=new ArrayList<>();
            for(UserModel model:list){
                UserVO vo=new UserVO();
                vo.setName(model.getName());
                vo.setUsername(model.getUsername());
                vo.setPassword(model.getPassword());
                vo.setCreateTime(DateUtil.format(model.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                vos.add(vo);
            }
            wrapper= ResponseWrapperMapper.successByData(vos,list.size());
        }else{
            wrapper=ResponseWrapperMapper.errorEnum(RespEnum.SYSTEM_HAS_NO_DATA);
        }
        return wrapper;*/
    }

    @Override
    public UserModel getUserByUsername(String username) {
        Example example=new Example(UserModel.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("username",username);
        return userMapper.selectOneByExample(example);
    }

    private static int ExpireTime = 60;

    @Override
    public ResponseWrapper getUserByRedis(String username) {
        ResponseWrapper wrapper;
        Object object=redisUtil.get(username);
        if(object !=null){
            log.info("从缓存中获取数据====================");
            wrapper=ResponseWrapperMapper.success();
            wrapper.setData(object);
        }else{
            log.info("从数据库获取数据=====================");
            UserModel model=this.getUserByUsername(username);
            UserVO vo=new UserVO();
            vo.setUserId(model.getId());
            vo.setUsername(model.getUsername());
            vo.setPassword(model.getPassword());
            vo.setName(model.getName());
            vo.setImage(model.getImage());
            vo.setCreateTime(DateUtil.format(model.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            redisUtil.set(username,vo,ExpireTime);
            wrapper=ResponseWrapperMapper.success();
            wrapper.setData(vo);
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper updateUser(UserDTO userDTO) {
        UserModel model=userMapper.selectByPrimaryKey(userDTO.getUserId());
        model.setName(userDTO.getName());
        userMapper.updateByPrimaryKey(model);
        return ResponseWrapperMapper.success();
    }

    @Override
    public List<RoleModel> getRoleListByUser(UserModel userModel) {
        log.info("userId:"+userModel.getId());
        return roleMapper.getRoleListByUserId(userModel.getId());
    }

    @Override
    public List<PermissionModel> getPermissionListByRoleId(int roleId) {
        log.info("roleId:"+roleId);
        //过滤掉权限为空的(默认一级菜单下的权限为空,为了不影响判断需要去掉)
        List<PermissionModel> temp= permissionMapper.getPermissionListByRoleId(roleId);
        List<PermissionModel> list=new ArrayList<>();
        for(PermissionModel model:temp){
            if(!StringUtils.isEmpty(model.getPer())){
              list.add(model);
            }
        }
        return list;
    }

    @Override
    public ResponseWrapper saveUser(UserDTO userDTO) {
        log.info("用户注册=====");
        //需要判断账户是否存在
        Example example=new Example(UserModel.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("username",userDTO.getUsername());
        UserModel model=userMapper.selectOneByExample(example);
        if(!StringUtils.isEmpty(model)){
            return ResponseWrapperMapper.errorEnum(RespEnum.REGISTER_USER_EXISTS);
        }
        //需要处理密码两次加密及盐值(此处salt=username+name)
         model=new UserModel();
        model.setUsername(userDTO.getUsername());
        model.setName(userDTO.getName());
        String salt=RandomUtil.randomStringUpper(16);
        model.setPassword(MD5Pwd(userDTO.getUsername(),userDTO.getPassword(),salt));
        model.setCreateTime(new Date());
        model.setSalt(salt);
        userMapper.insert(model);

        //缓存数据
        UserVO vo=new UserVO();
        vo.setUserId(model.getId());
        vo.setUsername(model.getUsername());
        vo.setPassword(model.getPassword());
        vo.setName(model.getName());
        vo.setCreateTime(DateUtil.format(model.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        redisUtil.set(userDTO.getUsername(),vo,ExpireTime);
        log.info("数据缓存到redis==============");
        return ResponseWrapperMapper.success();
    }

    @Override
    public ResponseWrapper addUserImg(String username, String name, String pwd, String img) {
        ResponseWrapper wrapper=null;
        //需要判断账户是否存在
        Example example=new Example(UserModel.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("username",username);
        UserModel model=userMapper.selectOneByExample(example);
        if(!StringUtils.isEmpty(model)){
           wrapper= ResponseWrapperMapper.errorEnum(RespEnum.REGISTER_USER_EXISTS);
        }else{
            model=new UserModel();
            model.setUsername(username);
            model.setName(name);
            String salt=RandomUtil.randomStringUpper(16);
            model.setPassword(MD5Pwd(username,pwd,salt));
            model.setCreateTime(new Date());
            model.setSalt(salt);
            model.setImage(img);
            userMapper.insert(model);
            wrapper=ResponseWrapperMapper.success();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper delUser(int userId) {
       userMapper.deleteByPrimaryKey(userId);
       return ResponseWrapperMapper.success();
    }


    private String MD5Pwd(String username, String pwd,String salt) {
        // 加密算法MD5
        // salt盐 username + salt
        // 迭代次数
        String md5Pwd = new SimpleHash("MD5", pwd,
                ByteSource.Util.bytes(username + salt), 2).toHex();
        return md5Pwd;
    }
}
