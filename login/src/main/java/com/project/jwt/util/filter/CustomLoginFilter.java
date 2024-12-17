package com.project.jwt.util.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.project.jwt.controller.security.MyUserDetails;
import com.project.jwt.util.JWTService;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    //UsernamePasswordAuthenticationFilter는 로그인을 담당하는 필터
    //사용자 로그인을 커스터마이징 시키려면 상속받고 연결해주면 됩니다.

    private AuthenticationManager authenticationManager;

    public CustomLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //post방식의 /login 요청이 들어오면, attemptAuthentication으로 연결됩니다.
    @Override     
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("attemptAuthentication 실행됨");

        //JASON타입이라면, request JSON데이터를 꺼내서, 사용할수 있도록 파싱
        String username = request.getParameter("username"); //폼방식의 데이터
        String password = request.getParameter("password");

        System.out.println(username);

        //UserNamePasswordAuthentication = (JSON 웹토큰 아님)
        //이 값을 인증매니저 AuthenticationManager 에게 전달
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        //유저 서비스의 loadByUser가 호출이 됩니다.
        Authentication authentication = authenticationManager.authenticate(token);
        //로그인 실패시 이값은 null
        System.out.println("필터: 로그인이 성공하면 이값이 있습니다:" + authentication);


        return authentication; //스프링 시큐리티가 이값을 가져가서, 시큐리티 세션에 등록을 시킴
    }

    //8. authenticationManager객체


    //로그인 성공시에는 이 메서드가 실행됨
   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
           Authentication authResult) throws IOException, ServletException {
       
       System.out.println("로그인 success후에 실행됨");
       //JWT토큰 발급

       MyUserDetails details = (MyUserDetails)authResult.getPrincipal();

       String token = JWTService.createToken(details.getUsername()); //토큰생성성

       //헤더에 담는다.
       response.setContentType("text/html; charset=UTF8");
       response.setHeader("Authorization", token);
       //데이터도 보낼수 있음
       response.getWriter().println("서버에서 보낸 메세지");

   }
 
   //로그인 실패시에는 이 메서드가 실행됨
   @Override
   protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
           AuthenticationException failed) throws IOException, ServletException {
       
        System.out.println("로그인 실패시 실행됨");

        //클라이언트로 에러를 보내줌 (레스트 방식) redirect로도 가능
        response.setContentType("text/plain; charset=UTF8");
        response.sendError(500, "아이디 비밀번호를 확인하세요");
   }
   
}
