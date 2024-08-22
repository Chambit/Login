package user.sequrity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import user.sequrity.filter.FilterOne;
import user.sequrity.filter.FilterTwo;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //패스워드 인코딩
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable(); //서버에 인증정보를 사용하지 않기에 csrf를 사용하지 않는다.
        http.formLogin().disable(); //form기반의 로그인에 대해 비 활성화 하며 커스텀으로 구성한 필터를 사용한다.
        http.httpBasic().disable(); //Authorization 아이디,비밀번호 = basic을 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //session기반의 인증을 사용하지않고 추후 JWT사용 예정

        http.authorizeRequests(auth -> auth.anyRequest().permitAll()); //토큰을 사용하는경우 모든요청에 대해 인가를 적용

        //2. 크로스 오리진 필터 디폴트 등록
        //3. 부메랑을 통해서 login요청 header값 확인
        http.cors(Customizer.withDefaults()); //WithDefault - Customizer인터페이스 확인

        //http.addFilter(new FilterOne()); //error => 너는 반드시 시큐리티유형의 필터만 여기에 추가할 수 있다. 고려해라 FilterBefore or FilterAfter
        //http.addFilterBefore(new FilterOne(), SecurityContextPersistenceFilter.class); //deprecated

        //4. 필터체이닝 연습하기
        http.addFilterBefore(new FilterOne(), UsernamePasswordAuthenticationFilter.class); //시큐리티 필터 이전에 등록
        http.addFilterAfter(new FilterTwo(), FilterOne.class); //FilterOne 이후에 등록
        //7. AuthenticationManager 얻기
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        //9. 로그인 진행시키기
        //10. 로그인 처리하기
        //http.addFilterBefore( new CustomLoginFilter(authenticationManager),  UsernamePasswordAuthenticationFilter.class ); //로그인진행 필터 - 이렇게 해도됩니다.
        return http.build();
    }
    //8. AuthenticationManager 객체
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //1. 크로스 오리진 필터 등록
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:8181")); //부메랑에서 요청이 deny됨
        configuration.setAllowedOrigins(Arrays.asList("*")); //모든요청 주소를 허용 = CrossOrigin
        configuration.setAllowedMethods(Arrays.asList("*")); //모든요청 메서드를 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
