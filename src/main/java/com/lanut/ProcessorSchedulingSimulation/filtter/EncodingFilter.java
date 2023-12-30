package com.lanut.ProcessorSchedulingSimulation.filtter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    // 定义一个成员变量用于存储编码参数值
    private String encoding;


    @Override
    public void init(FilterConfig filterConfig) {
        // 从配置文件中获取编码参数值，并赋值给成员变量
        encoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        // System.out.println(encoding);
        res.setContentType("text/html;charset=" + encoding);
        // 放行
        chain.doFilter(req, res);
    }

}
