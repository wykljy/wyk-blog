package com.wangyakun.boot.wykblog.model.vo;
import lombok.Data;
import java.io.Serializable;

/**
 * @ClassName RoleVO
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/2/5 16:19
 * @Version 1.0
 **/
@Data
public class RoleVO implements Serializable {
    private static final long serialVersionUID=1L;

    private int roleId;

    private String  roleName;

    private String roleDes;

    private String createTime;

    private boolean roleFlag;

}
