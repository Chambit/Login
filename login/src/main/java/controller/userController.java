package controller;

import command.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import user.jwt.util.JWTService;

import javax.servlet.http.HttpServletRequest;

public class userController {
    //로그인 시도라고 가정
    //토큰검증
    //POST
    //{"username": "xxx", "password" : "1234", "role" : "ROLE_USER"}

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserVO vo) {

        //로그인 이후에 성공이라고 가정
        System.out.println(vo.toString());
        //토큰생성
        String token = JWTService.createToken(vo.getUser_id());
        //토큰응답
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    //헤더 - Authorization: 토큰
    //토큰은 헤더에 담 사용자 정보와 함께 요청
    @PostMapping("/api/v1/getInfo")
    public ResponseEntity<Object> getInfo(HttpServletRequest request) {
        //토큰얻기
        String token = request.getHeader("Authorization");
        System.out.println("전달된 토큰:" + token);

        //토큰 무결성 검사
        try {
            boolean result = JWTService.validateToken(token);
            System.out.println("토큰 무결성:" + token);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("토큰위조", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("토큰인증", HttpStatus.OK);
    }


}
