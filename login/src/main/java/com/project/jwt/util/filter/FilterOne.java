package com.project.jwt.util.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class FilterOne implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

        //인증여부를 확인하는 필터
        System.out.println("필터원 실행됨");

//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse res = (HttpServletResponse)response;
//
//        String token = req.getHeader("Authorization"); //헤더에서 Authorization값을 얻는다
//
//        if(token != null && token.equals("token")) { //테스트 토큰
//            chain.doFilter(request, response); //다음 필터로 연결
//        } else {
//            res.setCharacterEncoding("utf-8");
//            PrintWriter out = res.getWriter();
//            out.println("<h3>인증안됨</h3>");
//        }
        chain.doFilter(request, response); //다음 필터로 연결
    }
}
