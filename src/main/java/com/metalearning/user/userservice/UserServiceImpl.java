package com.metalearning.user.userservice;

import com.metalearning.user.userdto.UserSignUpDTO;
import com.metalearning.user.userentity.UserEntity;
import com.metalearning.user.userentity.UserStatus;
import com.metalearning.user.userrepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean usersave(UserSignUpDTO userSignUpDTO) {
        // 이메일 중복 체크
        if (userRepository.findByUserEmail(userSignUpDTO.getUserEmail()).isPresent()) {
            return false;  // 이미 사용 중인 이메일이라면 회원가입 실패
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userSignUpDTO.getUserPw());

        // UserEntity 객체 생성 후 저장
        UserEntity userEntity = UserEntity.builder()
                .userEmail(userSignUpDTO.getUserEmail()) // 이메일
                .userPw(encodedPassword) // 암호화된 비밀번호
                .name(userSignUpDTO.getName()) // 이름
                .userGender(userSignUpDTO.getUserGender()) // 성별
                .userMarketingAgree(userSignUpDTO.getUserMarketingAgree()) // 마케팅 동의
                .userPrivacyAgree(userSignUpDTO.getUserPrivacyAgree()) // 개인정보 동의
                .userBirth(userSignUpDTO.getUserBirth()) // 생일
                .userPhone(userSignUpDTO.getUserPhone()) // 전화번호
                .userAddress(userSignUpDTO.getUserAddress()) // 주소
                .userRole("USER")  // 기본 역할은 "USER"
                .userStatus(UserStatus.ACTIVE)  // 기본 상태는 "ACTIVE"
                .build();

        // UserEntity 저장
        userRepository.save(userEntity);

        return true;  // 회원가입 성공
    }

    //모든 회원 정보 가져오기
    @Override
    public List<UserSignUpDTO> userall() {
        List<UserEntity> userEntities = userRepository.findAll();

        // UserEntity 리스트를 UserSignUpDTO 리스트로 변환
        List<UserSignUpDTO> userall = userEntities.stream()
                .map(user -> new UserSignUpDTO(
                        user.getUserId(),
                        user.getUserEmail(),
                        user.getUserPw(),
                        user.getUserRole(),
                        user.getName(),
                        user.getUserGender(),
                        user.getUserBirth(),
                        user.getUserPhone(),
                        user.getUserPostcode(),
                        user.getUserAddress(),
                        user.getUserAddressDetail(),
                        user.getUserEduLevel(),
                        user.getUserMarketingAgree(),
                        user.getUserPrivacyAgree(),
                        user.getUserStatus(),
                        user.getUserCreatedAt(),
                        user.getUserUpdatedAt(),
                        user.getUserSns(),
                        user.getUserThumbnail(),
                        user.getUserLastLogin()
                ))
                .collect(Collectors.toList());

        return userall;
    }


}
