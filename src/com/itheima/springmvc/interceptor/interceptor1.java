package com.itheima.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件说明
 * Author JiJG(jijg)<jijg@lizi.com>
 * date:2018-03-05 21:07
 */
public class interceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //方法前
        System.out.println("方法前1");
        // true 放行 false 拦截
        String requestURI = httpServletRequest.getRequestURI();
        if (!requestURI.contains("/login")) {
            String username = (String) httpServletRequest.getSession().getAttribute("USER_SESSION");
            if (null == username) {

                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.action");
                return  false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("方法后1");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("页面渲染后1");
    }
}
