package com.wangyakun.boot.wykblog.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @ClassName CaptchaUtils
 * @Description 验证码工具类
 * @Author wangyakun
 * @Date 2020/1/31 14:48
 * @Version 1.0
 **/
public class CaptchaUtils {
    public  Logger log= Logger.getLogger(CaptchaUtils.class);

    public void saveCaptcha (){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String basePath= CaptchaUtils.class.getClassLoader().getResource("static/assert/image/captcha/").getPath();
        log.info("basePath:"+basePath);
        String fileName="lime.png";
        File file =new File(basePath+fileName);
        lineCaptcha.write(file);
        log.info("captcha code info:"+lineCaptcha.getCode());
        boolean b=lineCaptcha.verify("123456");
        if(b){
            log.info("验证码正确");
        }else{
            log.info("验证码错误");
        }
    }

    public static void main(String[] args) {
        CaptchaUtils utils=new CaptchaUtils();
        utils.saveCaptcha();
    }


}
