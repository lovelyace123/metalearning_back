package com.metalearning.security;

import com.metalearning.user.userentity.UserEntity;
import com.metalearning.user.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail)
            throws UsernameNotFoundException {
        // DB에서 username으로 회원이 맞는지 체크한 후 맞다면 UserDetails 객체를 반환
        Optional<UserEntity> opt = userRepository.findByUserEmail(userEmail);  // 이메일로 검색
        opt.orElseThrow(() -> new UsernameNotFoundException("인증에 실패했습니다. 아이디와 비밀번호를 확인하세요"));
        return opt.get();
    }
}
