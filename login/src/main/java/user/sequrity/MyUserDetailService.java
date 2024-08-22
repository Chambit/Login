package user.sequrity;

import command.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    //loginProcessingUrl이 호출될때 자동으로 호출시킵니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("form에서 전달된 아이디:" + username);
        //로그인을 시도합니다. (비밀번호는 시큐리티가 알아서 처리합니다.)
        UserVO vo = userMapper.login(username);

        //로그인이 성공했음
        if(vo != null) {
            return new MyUserDetails(vo); //여기서 반환되는 리턴은 = 시큐리티세션 ( new Authentication( new MyUserDetails()) ) 형식으로 전달됩니다.
        }
        return null;
    }
}
