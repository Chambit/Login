package com.project.jwt.controller.security;

import com.project.jwt.command.UserVO;
import com.project.jwt.controller.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    //loginProcessingUrl이 호출될때 자동으로 호출시킵니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("사용지가 로그인을 시도함");
        System.out.println("사용자가 입력한아이디:" + username);
        //로그인을 시도합니다. (비밀번호는 시큐리티가 알아서 처리합니다)
        UserVO vo = memberMapper.login(username);

        System.out.println("여기는 안되냐?");
        System.out.println(vo);

        //로그인이 성공했음
        if(vo != null) {
            return new MyUserDetails(vo); // 여기서 반환되는 리턴은 = 시큐리티세션( new Authentication(new MyUserDetails()) ) 형식으로 전달됩니다.
        }

        //유저명을 찾지 못하면 에러, login?error로 이동됩니다.
        //비밀번호가 틀리 더라도 에러, login?error로 이동됩니다.
        return null;
    }

}