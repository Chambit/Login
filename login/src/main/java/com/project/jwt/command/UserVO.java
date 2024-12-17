package com.project.jwt.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private String user_id;
    private String user_pw;
    private String role;
    private String user_nm;
    private int user_age;
    private String user_gn;
    private String user_pn;
    private String parn_pn;

    private String  password;
    private String  username;
    private boolean isacountNonExpired;
    private boolean isacountnonlocked;
    private boolean iscreadentialsnonexpired;
    private boolean isenable;


}
