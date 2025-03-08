package com.acme.biz.web.mvc.servlet.idempotent;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 通过sessions中的token 进行幂等拦截校验
 * @author: wuhao
 * @time: 2025/3/8 22:39
 */
public class IdempotentFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //使用 HttpSeason Id -> cookies 来自headers
        String token = request.getParameter("token");
        //集群情况下面,第一次请求和第二次请求在不同机器上面,也会生成不同的token
        // HttpSession结合Redis  利用Spring session操作
        // HttpSession#setAttribute 底层利用redis hash 来实现 全局唯一
        HttpSession httpSession = request.getSession(false);
        Object value = httpSession.getAttribute(token);

        if(value !=null){
            throw new ServletException("幂等校验失败");
        }
        //设定状态
        httpSession.setAttribute(token,token);

        try{
            doFilter(request,response,filterChain);
        }finally {
            httpSession.removeAttribute(token);
        }

    }
}
