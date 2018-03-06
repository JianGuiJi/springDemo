package com.itheima.springmvc.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件说明
 * Author JiJG(jijg)<jijg@lizi.com>
 * date:2018-03-05 13:39
 */

/**
 * 由spring 管理 WEB-INF/dispatcher-servlet.xml
 * SpringMVC的异常处理器
 * <bean class="com.itheima.springmvc.exception.CustomExceptionResolver"/>
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // TODO Auto-generate method stub  发送异常的地方 Service 层 方法 包名+类名+ 方法名（形参）字符串
        //日志 1 发布tomcat war Eclipse/idea 2 tomcat 服务器上 Linux Log4j
        ModelAndView mav = new ModelAndView();

        //判断异常类型 预期异常 还是运行时异常
        if (e instanceof MessageException) {
            //预期异常
            MessageException me = (MessageException) e;

            mav.addObject("error", me.getMsg());
        } else {
            mav.addObject("error", "未知异常");
        }

        mav.setViewName("error");
        return mav;
    }
}
