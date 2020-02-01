package com.wangyakun.boot.wykblog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CaptchaController
 * @Description 生成验证码
 * @Author wangyakun
 * @Date 2020/1/31 16:24
 * @Version 1.0
 **/
@Controller
@RequestMapping("/captcha")
public class CaptchaController {
    public final Logger log=Logger.getLogger(CaptchaController.class);
    @RequestMapping("/getImg")
    public void saveCaptchaImage(HttpServletRequest request, HttpServletResponse response)throws  Exception{
        log.info("生成验证码=================");
        response.setContentType("image/png");
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.write(response.getOutputStream());
        //缓存验证码
        request.getSession().setAttribute("verity",lineCaptcha.getCode());
    }
}
