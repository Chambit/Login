package com.project.jwt.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;

public class JwtAuthoticationFilter extends BasicAuthenticationFilter{
    //BasicAuthenticationFilter - HTTP요청의 (BASIC)인증 헤더를 처리하여 결과를 SecurityContextHolder에 저장합니다.

    //생성자는 반드시 authenticationManager를 받아줌
    public JwtAuthoticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        System.out.println("================JWT basic헤더 검사하는 필터 실행됨========================");

    }

}
