package com.project.jwt.controller;

import com.project.jwt.util.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//@RestController
@RestController
public class APIController {

    //JWT - API기반의 인증 정보를 처리할 때, 토큰을 발급(발행자)

//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody UserVO vo) {
//
//        //사용자 정보를 받아서 로그인처리
//        //ok 로그인 성공했어
//        //토큰 발행
//        String token = JWTService.createToken(vo.getUsername());
//
//        return new ResponseEntity<>(token, HttpStatus.OK);
//    }

    // @PostMapping("/api/v1/getInfo")
    // public ResponseEntity<Object> getInfo(HttpServletRequest request){

    //     //클라이언트에서 토큰을 header라는곳에 담아 보냅니다.
    //     //토큰을 전달받아서, 유효성을 확인한 후에, 만료인지 통과인지 확인
    //     String token = request.getHeader("Authorization");
    //     System.out.println("전달된 토큰:" + token);

    //     //토큰이 유효한가?
    //     try {
    //         boolean result = JWTService.validateToken(token);
    //         System.out.println("토큰의 무결성 여부:" + result);
    //     } catch ( Exception e ) {
    //         e.printStackTrace(); //예외로 들어왔다? -> 토큰위조됨
    //         return new ResponseEntity<>("토큰위조", HttpStatus.UNAUTHORIZED); //토큰위조
    //     }

    //     return new ResponseEntity<>("통과된 사람이면 여기에 회원정보 발급", HttpStatus.OK);
    // }

    //필터 동작 확인
    @GetMapping("/api/v1/hello")
    public String hello() {
        return "<h3>hello</h3>";
    }

    @PostMapping("/api/v1/getInfo")
    public ResponseEntity<Object> getInfo(HttpServletRequest request){
        
        System.out.println("이 getInfo메서드는 token이 있는 유저만 호출할 권리가 있다");

        return new ResponseEntity<>("getInfo~~", HttpStatus.OK);
    }

}
