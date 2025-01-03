package com.metalearning.user.userservice;

import com.metalearning.KDT.KDTdto.KDTStaffDTO;
import com.metalearning.KDT.KDTentity.KDTSessionEntity;
import com.metalearning.KDT.KDTentity.KDTStaffEntity;
import com.metalearning.KDT.KDTrepository.KDTSessionRepository;
import com.metalearning.KDT.KDTrepository.KDTStaffRepository;
import com.metalearning.user.userdto.UserSignUpDTO;
import com.metalearning.user.userentity.UserEntity;
import com.metalearning.user.userentity.UserStatus;
import com.metalearning.user.userrepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final KDTStaffRepository kdtStaffRepository;
    private final KDTSessionRepository kdtSessionRepository;

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

    //권한이 매니저인 사람만 찾기
    @Override
    public List<UserSignUpDTO> usermanagerall(Long sessionId) {
        // 'MANAGER' 역할을 가진 사용자만 조회
        List<UserEntity> userEntities = userRepository.findByUserRole("MANAGER");

        // 해당 세션에 이미 등록된 강사들의 userId를 조회
        List<Long> alreadyRegisteredUserIds = kdtStaffRepository.findByKdtSessionEntity_KdtSessionId(sessionId).stream()
                .map(kdtStaff -> kdtStaff.getUserEntity().getUserId())
                .collect(Collectors.toList());

        // 이미 등록되지 않은 강사만 필터링
        List<UserSignUpDTO> userall = userEntities.stream()
                .filter(user -> !alreadyRegisteredUserIds.contains(user.getUserId()))  // 등록되지 않은 강사만 필터링
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

    //권한이 매니저이면서 세션에 등록된 사람만 찾기
    @Override
    public List<UserSignUpDTO> userRegisteredManager(Long sessionId) {
        // 'MANAGER' 역할을 가진 사용자들만 조회
        List<UserEntity> userEntities = userRepository.findByUserRole("MANAGER");

        // 해당 세션에 이미 등록된 매너저들의의 userId를 조회
        List<Long> alreadyRegisteredUserIds = kdtStaffRepository.findByKdtSessionEntity_KdtSessionId(sessionId).stream()
                .map(kdtStaff -> kdtStaff.getUserEntity().getUserId())
                .collect(Collectors.toList());

        // 이미 등록된 매니저만 필터링
        List<UserSignUpDTO> registeredmanager = userEntities.stream()
                .filter(user -> alreadyRegisteredUserIds.contains(user.getUserId()))  // 등록된 매니저만
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

        return registeredmanager ;
    }

    //권한이 강사이면서 세션에 등록된 안된  사람만 찾기
    @Override
    public List<UserSignUpDTO> userinstrall(Long sessionId) {
        // 'INSTRUCTOR' 역할을 가진 사용자들만 조회
        List<UserEntity> userEntities = userRepository.findByUserRole("INSTRUCTOR");

        // 해당 세션에 이미 등록된 강사들의 userId를 조회
        List<Long> alreadyRegisteredUserIds = kdtStaffRepository.findByKdtSessionEntity_KdtSessionId(sessionId).stream()
                .map(kdtStaff -> kdtStaff.getUserEntity().getUserId())
                .collect(Collectors.toList());

        // 이미 등록되지 않은 강사만 필터링
        List<UserSignUpDTO> userall = userEntities.stream()
                .filter(user -> !alreadyRegisteredUserIds.contains(user.getUserId()))  // 등록되지 않은 강사만 필터링
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

    //강사이면서 세션이미 등록된 사람만 찾는거임
    @Override
    public List<UserSignUpDTO> userRegisteredInstructors(Long sessionId) {
        // 'INSTRUCTOR' 역할을 가진 사용자들만 조회
        List<UserEntity> userEntities = userRepository.findByUserRole("INSTRUCTOR");

        // 해당 세션에 이미 등록된 강사들의 userId를 조회
        List<Long> alreadyRegisteredUserIds = kdtStaffRepository.findByKdtSessionEntity_KdtSessionId(sessionId).stream()
                .map(kdtStaff -> kdtStaff.getUserEntity().getUserId())
                .collect(Collectors.toList());

        // 이미 등록된 강사만 필터링
        List<UserSignUpDTO> registeredInstructors = userEntities.stream()
                .filter(user -> alreadyRegisteredUserIds.contains(user.getUserId()))  // 등록된 강사만 필터링
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

        return registeredInstructors;
    }


    @Override
    public int instrsave(KDTStaffDTO kdtStaffDTO) {
        // 세션 ID가 유효한지 체크
        KDTSessionEntity sessionEntity = kdtSessionRepository.findById(kdtStaffDTO.getKdtSessionId())
                .orElseThrow(() -> new IllegalArgumentException("세션이 존재하지 않습니다."));

        // 강사 및 매니저들을 저장할 리스트
        List<KDTStaffEntity> kdtStaffEntities = new ArrayList<>();

        // 강사 및 매니저 ID 목록을 순회하며 강사 등록 처리
        for (Long userId : kdtStaffDTO.getUserIds()) {
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다."));

            // 해당 세션과 강사 조합 및 매니저가 이미 존재하는지 확인
            boolean alreadyExists = kdtStaffRepository.existsByKdtSessionEntityAndUserEntity(sessionEntity, userEntity);

            if (alreadyExists) {
                // 이미 존재하는 경우, 강사 등록을 하지 않고 skip
                return 2;  // 이미 등록된 경우, 2를 반환 (중복된 강사)
            }

            // 강사 및 매니저 등록을 위한 엔티티 생성
            KDTStaffEntity kdtStaffEntity = KDTStaffEntity.builder()
                    .kdtSessionEntity(sessionEntity)
                    .userEntity(userEntity)
                    .build();

            // 생성된 매니저 및 강사 엔티티를 리스트에 추가
            kdtStaffEntities.add(kdtStaffEntity);
        }

        // saveAll()을 사용하여 여러 개의 강사 매니저 엔티티를 한 번에 저장
        if (!kdtStaffEntities.isEmpty()) {
            kdtStaffRepository.saveAll(kdtStaffEntities);
            return 1; // 강사 or 매니저 등록 성공
        }

        // 강사 등록 실패 (비어있는 경우)
        return 0; // 실패 (강사가 없거나, 다른 이유로 저장되지 않은 경우)
    }



}
