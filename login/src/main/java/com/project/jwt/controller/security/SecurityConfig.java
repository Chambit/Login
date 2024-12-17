package com.project.jwt.controller.security;

import java.util.Arrays;

import com.project.jwt.util.filter.CustomLoginFilter;
import com.project.jwt.util.filter.JwtAuthoticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //비밀번호 암호화 객체
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("실행됨?");
        //1. 기본설정
        http.csrf().disable();
        http.formLogin().disable(); //form 로그인 기반 폐기
        http.httpBasic().disable(); //http 기본 인증형태 폐기 Authorization에는 원래(아이디, 비밀번호) 형태인데, 이것을 폐기 (토큰방식을 사용하니까)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션기반의 인증 폐기

        //2. 크로스 오리진 필터 활성화
        http.cors( Customizer.withDefaults() );

        //3. 시큐리티에 필터추가
        //http.addFilter( new FilterOne() ); //addFilter에는 시큐리티 타입의 필터만 적용됨.

        //시큐리티 타입의 필터가 아니라, 일반 필터라면, 시큐리티 전후로 필터를 추가.
        //사용자 정의 필터를 추가하고싶으면 이렇게 하면 됨
        //필터체인지 연습
//        http.addFilterBefore(new FilterOne(), UsernamePasswordAuthenticationFilter.class); //시큐리티 타입의 필터보다 이전에 커스터마이징한 필터를 등록
//        http.addFilterAfter(new FilterTwo(), FilterOne.class);

        //7. authenticationManager얻기
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        //스프링 시큐리티 타입의 필터를 커스터마이징해서 사용 (로그인) 
        //클라이언트에서 /login에 post방식으로 요청
        // http.addFilter( new CustomLoginFilter(authenticationManager) ); //로그인필터
        // http.addFilter( new JwtAuthoticationFilter(authenticationManager)); //JWT인증필터


        //login경로에만 커스텀로그인 필터가 실행됨
        // http.requestMatchers().antMatchers("/login")
        //                       .and()
        //                       .addFilter(new CustomLoginFilter(authenticationManager));

        // http.requestMatchers().antMatchers("/api/v1/**") // /api/v1 경로에만 들어감
        //                       .antMatchers(null)
        //                       .and()
        //                       .addFilter(new JwtAuthoticationFilter(authenticationManager));

        return http.build();
    }


    //크로스 오리진 필터 - 서버가 다를떄, 옵션 요청을 보내게 되는데, 옵션요청에 요청을 허용할 도메인 주소를 헤더에 담아 보내는작업.
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); //모든 요청주소를 허용함
        configuration.setAllowedMethods(Arrays.asList("*")); //모든 요청메서드를 허용함
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);//모든 요청에 대해서

        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
