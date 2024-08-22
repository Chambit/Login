package user.sequrity.filter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import user.jwt.util.JWTService;
import user.sequrity.MyUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    //2. authenticationManager를 시쿠리티설정에서 전달하기
    public CustomLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //로그인을 시도함
    @Override
    public Authentication attemtAuthentication(HttpServletRequest request, HttpResponse response)
    throws AuthenticationException {
        System.out.println("=======attemtAuthentication=======");

        //1. /login요청으로 들어오는 username userpassword의 폼 형식 로그인데이터를 얻는다
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username);
        System.out.println(password);

        //3.로그인 실행시키기 - 시큐리티 로그인 기능이 사용하는 전용토큰에 아이디, 비밀번호를 담습니다.
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        //어쏀티케이션매니저의 authenticate가 실행되면 MyUserDetailService의 loadUserByUsername가 실행됩니다
        //여기 리턴이 있다는것은 로그인 성공이라는 의미
        Authentication authentication = authenticationManager.authenticate(token);

        System.out.println("====로그인성공====");
        System.out.println("실행됨?" + authentication);

        return authentication; //리턴 (여기서 리턴된 값은 시큐리티세션( new Authentication(new MyUserDetails()) ) 형식으로 저장됩니다.
    }

    //successfulAthentication오버라이딩 - 로그인성공시 이후에 실행.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authresult)
        throws IOException, ServletException {

        System.out.println("======로그인 성공 success 핸들러=======");
        //어쎈티케이션안에는 new Authentication(new MyUserDetails()) 형식으로 유저객체가 저장됩니다.
        MyUserDetails principal = (MyUserDetails)authresult.getPrincipal(); //어쎈티케이션 안에 유저객체를 얻습니다.

        System.out.println(principal.getUsername());

        System.out.println("========JWT 토큰 생성 후 header에 담기========");

        String token = JWTService.createToken(principal.getUsername());

        response.setContentType("text/html; charset=UTF8;");
        response.addHeader("Authorization", "Bearer" + token);
        response.getWriter().println("로그인성공(아이디):" + principal.getUsername());
    }

    //unsuccessfullathentication 오버라이딩
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException faild)
        throws IOException, ServletException {

        System.out.println("=====로그인 성공 핸들러=====");
        System.out.println("인증에 실패함(로그인 실패)");

        response.setContentType("text/plain; charset=UTF8");
        response.sendError(500, "아이디 비밀번호를 확인하세요.");
    }
}
