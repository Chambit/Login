package user.jwt.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class JWTService {
    private static String secretKey = "chamdol";

    public static String createToken(String userName) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        long expire = System.currentTimeMillis() + 3600000;

        JWTCreator.Builder builder = JWT.create()
                .withSubject(userName) //토큰의 주제(subject) 설정
                .withIssuedAt(new Date()) //토큰 발급시간 설정 (현재 시간)
                .withExpiresAt(new Date(expire)) //토큰 만료시간 설정
                .withIssuer("주인장") //토큰 발행자
                .withClaim("admin", "나야나"); //공개클래임

        return builder.sign(algorithm);
    }

    public static boolean validateToken(String token) throws JWTVerificationException { //확인중 하나라도 실패
        Algorithm algorithm = Algorithm.HMAC256(secretKey); //검증 알고리즘
        JWTVerifier verifier = JWT.require(algorithm).build(); //token을 검증할 객체생성

        verifier.verify(token); //토큰 검증을 수행하며, 만료시간도 자동으로 검사됨

        return true;
    }
}
