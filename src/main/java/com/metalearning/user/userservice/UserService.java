package com.metalearning.user.userservice;

import com.metalearning.user.userdto.UserSignUpDTO;

import java.util.List;

public interface UserService {

    //회원정보 저장하는 메서드
    boolean usersave(UserSignUpDTO userSignUpDTO);

    //회원들 정보 전체 찾는 메서드
    List<UserSignUpDTO> userall();

    //권한이 매니저인 모든 정보 찾는 메서드
    List<UserSignUpDTO> usermanagerall();

    //권한이 강사인 모든 정보 찾는 메서드
    List<UserSignUpDTO> userinstrall();
}


