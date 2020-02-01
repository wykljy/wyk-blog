package com.wangyakun.boot.wykblog.controller;

import com.wangyakun.boot.wykblog.constant.RespEnum;
import com.wangyakun.boot.wykblog.model.vo.IndexPageVO;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName IndexController
 * @Description 引导页
 * @Author wangyakun
 * @Date 2020/1/12 20:35
 * @Version 1.0
 **/
@Controller
public class IndexController {
    public final Logger log = Logger.getLogger(IndexController.class);

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String defaultLogin(){
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper login(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("verity")String verity, HttpServletRequest request) {
        ResponseWrapper wrapper = null;
        log.info("执行用户登录=============");
        //从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        //在认证前提交令牌token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        int temp = 0;
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.info("未知账户=============");
            temp = 1;
        } catch (IncorrectCredentialsException ice) {
            log.info("密码错误================");
            temp = 2;
        } catch (LockedAccountException lae) {
            log.info("账户已锁定");
            temp = 3;
        } catch (ExcessiveAttemptsException eae) {
            log.info("用户名或者密码错误次数过多");
            temp = 4;
        } catch (AuthenticationException ae) {
            log.info("用户名或者密码不正确");
            temp = 5;
        }
        if (subject.isAuthenticated()) {
            //需要校验验证码
            if(!verity.equalsIgnoreCase((String)request.getSession().getAttribute("verity"))){
                wrapper=ResponseWrapperMapper.errorMsg("验证码错误");
            }else {
                log.info("登录成功");
                wrapper = ResponseWrapperMapper.success();
            }
        } else {
            token.clear();
            log.info("登录失败");
            switch (temp) {
                case 1:
                    wrapper = ResponseWrapperMapper.errorEnum(RespEnum.LOGIN_ACCOUNT_NOT_EXISTS_ERROR);
                    break;
                case 2:
                    wrapper = ResponseWrapperMapper.errorEnum(RespEnum.LOGIN_PASSWORD_ERROR);
                    break;
                case 3:
                    wrapper = ResponseWrapperMapper.errorEnum(RespEnum.LOGIN_ACCOUNT_IS_LOCK);
                    break;
                case 4:
                    wrapper = ResponseWrapperMapper.errorEnum(RespEnum.LOGIN_USERNAME_OR_PASSWORD_NOT_ONE);
                    break;
                case 5:
                    wrapper=ResponseWrapperMapper.errorEnum(RespEnum.LOGIN_USERNAME_OR_PASSWORD_ERROR);
                    break;

            }
        }
        return wrapper;
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        log.info("------没有权限-------");
        return "403";
    }

    @RequestMapping("/logout")
    public String logout(){
        //shiro 登出
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        log.info("用户退出登录");
        return "login";
    }

    @RequestMapping("getIndexPageData")
    @ResponseBody
    public ResponseWrapper getIndexPageData(){
        IndexPageVO vo=new IndexPageVO();
        vo.setArticleCount(87);
        vo.setVipCount(298);
        vo.setCommentCount(230);
        return ResponseWrapperMapper.successByData(vo);
    }


}
