package com.project.jwt.controller.member;

import com.project.jwt.command.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
     void join(UserVO vo);
     UserVO login(String username);
}
