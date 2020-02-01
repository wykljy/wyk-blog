package com.wangyakun.boot.wykblog.controller;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/1/28 23:05
 * @Version 1.0
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    public final Logger log=Logger.getLogger(TestController.class);

    @RequestMapping("/error")
    @ResponseBody
    public ResponseWrapper  testException(@RequestParam("mark")String mark) throws  Exception{
        if(mark.equalsIgnoreCase("error")){
            int temp= 9/0;
            log.info("temp:"+temp);
            throw  new Exception("除数不能等于0");
        }
        return ResponseWrapperMapper.success();

    }

    @RequestMapping("/saveImg")
    @ResponseBody
    public ResponseWrapper testSaveImg(HttpServletResponse response)throws  Exception{
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.write(response.getOutputStream());
        log.info(lineCaptcha.getCode());
        return ResponseWrapperMapper.success();
    }

}
