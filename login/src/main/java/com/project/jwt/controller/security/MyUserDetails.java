package com.project.jwt.controller.security;

import com.project.jwt.command.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    //멤버변수 선언
    private UserVO userVO;
    //객체생성
    public MyUserDetails(UserVO userVO) {
        this.userVO = userVO;
    }

    //유저의 role을 화면에서 사용하기 위해 getter생성
    public String getRole() {
        return userVO.getRole();
    }

    //로그인시 권한을 리턴해주는 함수
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return userVO.getRole(); //유저의 권한을 넣습니다.
            }
        });
        return list; //유저객체의 role을 뽑아서 컬렉션에 담아 리턴합니다.
    }

    @Override
    public String getPassword() {
        return userVO.getPassword(); //유저의 비밀번호를 반환하는 자리입니다.
    }

    @Override
    public String getUsername() {
        return userVO.getUsername(); //유저의 아이디를 반환하는 자리입니다.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //계정이 만료되지 않았습니까? (true = 네)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //계정이 락이 걸리지 않았습니까?
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //비밀번호 만료되지 않았습니까?
    }

    @Override
    public boolean isEnabled() {
        return true; //계정 사용할 수 있습니까?
    }

}
