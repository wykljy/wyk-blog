package com.wangyakun.boot.wykblog.service.impl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.wangyakun.boot.wykblog.constant.RespEnum;
import com.wangyakun.boot.wykblog.mapper.RoleMapper;
import com.wangyakun.boot.wykblog.model.RoleModel;
import com.wangyakun.boot.wykblog.model.dto.RoleDTO;
import com.wangyakun.boot.wykblog.model.vo.RoleVO;
import com.wangyakun.boot.wykblog.service.RoleService;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/26 16:37
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {
    public final Logger log=Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public ResponseWrapper queryRoleList(RoleDTO roleDTO) {
        ResponseWrapper wrapper;
        PageHelper.startPage(roleDTO.getPage(),roleDTO.getLimit());
        Example example=new Example(RoleModel.class);
        example.orderBy("id").desc();
        List<RoleModel> list=roleMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(list)){
            List<RoleVO> arr=new ArrayList<>();
            for(RoleModel model:list){
               RoleVO temp=new RoleVO();
               temp.setRoleId(model.getId());
               temp.setRoleName(model.getRoleName());
               temp.setRoleDes(model.getRoleDes());
               temp.setCreateTime(DateUtil.format(model.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
               temp.setRoleFlag(model.getRoleFlag());
               arr.add(temp);
            }
            wrapper= ResponseWrapperMapper.successByData(arr);
        }else{
            wrapper=ResponseWrapperMapper.errorEnum(RespEnum.SYSTEM_HAS_NO_DATA);
        }
        return wrapper;
    }
}
