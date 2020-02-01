package com.wangyakun.boot.wykblog.handler;
import com.wangyakun.boot.wykblog.util.ResponseWrapper;
import com.wangyakun.boot.wykblog.util.ResponseWrapperMapper;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 统一异常处理
 * @Author wangyakun
 * @Date 2020/1/28 22:51
 * @Version 1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    public final Logger log= Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseWrapper handlerException(HttpServletRequest request,Exception e){
        if(e instanceof UnauthorizedException){
            return ResponseWrapperMapper.errorMsg("权限不足,请联系管理员!!!");
        }
        log.info("system exception=================");
        log.info("url:"+request.getRequestURL().toString());
        return ResponseWrapperMapper.errorMsg(e.getMessage());
    }
}
