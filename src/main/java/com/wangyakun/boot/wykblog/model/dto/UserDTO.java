package com.wangyakun.boot.wykblog.model.dto;
import lombok.Data;
import java.io.Serializable;

/**
 * @ClassName UserDTO
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/27 22:51
 * @Version 1.0
 **/
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private int userId;

    private String username;

    private String password;

    private String name;

    private int page;

    private int limit;




}
