package controller;

import command.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import user.jwt.util.JWTService;

@RestController
public class APIController {

    //JWT - API기반의 인증 정보를 처리할 때, 토큰을 발급(발행자)

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserVO vo) {

        //사용자 정보를 받아서 로그인처리
        //ok 로그인 성공했어
        //토큰 발행
        String token = JWTService.createToken(vo.getUsername());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

//    @PostMapping("/api/v1/getInfo")
//    public ResponseEntity<Object> getInfo(){
//
//        //클라이언트에서 토큰을 header라는곳에 담아 보냅니다.
//        //토큰을 전달받아서, 유효성을 확인한 후에, 만료인지 통과인지 확인
//
//    }

}
