package com.metalearning.user.userservice;

import com.metalearning.KDT.KDTdto.KDTStaffDTO;
import com.metalearning.user.userdto.UserSignUpDTO;

import java.util.List;

public interface UserService {

    //회원정보 저장하는 메서드
    boolean usersave(UserSignUpDTO userSignUpDTO);

    //회원들 정보 전체 찾는 메서드
    List<UserSignUpDTO> userall();

    //권한이 매니저인 세션 등록 안댄 정보 찾는 메서드
    List<UserSignUpDTO> usermanagerall(Long sessionId);

    //권한이 매니저인 세션 등록댄 정보 찾는 메서드
    List<UserSignUpDTO> userRegisteredManager(Long sessionId);

    //권한이 강사인 모든 정보 찾는 메서드
    List<UserSignUpDTO> userinstrall(Long sessionId);

    //권한이 강사이며 세션에 등록된 유저 찾는 메서드임
    List<UserSignUpDTO> userRegisteredInstructors(Long sessionId);


    //회차별 매니저나 강사 넣는 메서드
    int instrsave(KDTStaffDTO kdtStaffDTO);



}




