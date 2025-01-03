package com.metalearning.KDT.KDTservice;

import com.metalearning.KDT.KDTdto.KDTCourseDTO;
import com.metalearning.KDT.KDTdto.KDTSessionDTO;
import com.metalearning.KDT.KDTentity.KDTCourseEntity;
import com.metalearning.KDT.KDTentity.KDTSessionEntity;
import com.metalearning.KDT.KDTentity.KDTStaffEntity;
import com.metalearning.KDT.KDTrepository.KDTCourseRepository;
import com.metalearning.KDT.KDTrepository.KDTSessionRepository;
import com.metalearning.KDT.KDTrepository.KDTStaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KdtServiceImpl implements KdtService {

    private final KDTCourseRepository kdtCourseRepository;
    private final KDTSessionRepository kdtSessionRepository;
    private final KDTStaffRepository kdtStaffRepository;


    //국비과정 등록하는 메서드임
    @Override
    public int kdtcoursesave(KDTCourseDTO kdtCourseDto) {
        try {
            // 중복 확인 사용하는거임
            boolean exists = kdtCourseRepository.existsByKdtCourseTitle(kdtCourseDto.getKdtCourseTitle());
            if (exists) {
                return 2;
            }
            KDTCourseEntity kdtCourseEntity = KDTCourseEntity.builder()
                    .kdtCourseTitle(kdtCourseDto.getKdtCourseTitle())
                    .kdtCourseStatus(kdtCourseDto.getKdtCourseStatus())
                    .kdtCourseType(kdtCourseDto.getKdtCourseType())
                    .build();
            kdtCourseRepository.save(kdtCourseEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //회차 등록하는 메서드임
    @Override
    public int kdtsessionsave(KDTSessionDTO kdtSessionDto) {
        try {
            // 코스 ID와 세션 번호를 기준으로 이미 존재하는 세션이 있는지 확인
            boolean exists = kdtSessionRepository.existsByKdtCourseEntity_KdtCourseIdAndKdtSessionNum(
                    kdtSessionDto.getKdtCourseId(), kdtSessionDto.getKdtSessionNum());

            if (exists) {
                return 2; // 이미 세션 번호가 존재하는 경우
            }
            // 세션 엔티티 생성
            KDTSessionEntity kdtSessionEntity = KDTSessionEntity.builder()
                    .kdtCourseEntity(KDTCourseEntity.builder().kdtCourseId(kdtSessionDto.getKdtCourseId()).build()) // 관계 설정
                    .kdtSessionNum(kdtSessionDto.getKdtSessionNum())
                    .kdtSessionTitle(kdtSessionDto.getKdtSessionTitle())
                    .kdtSessionDescript(kdtSessionDto.getKdtSessionDescript())
                    .kdtSessionStartDate(kdtSessionDto.getKdtSessionStartDate())
                    .kdtSessionEndDate(kdtSessionDto.getKdtSessionEndDate())
                    .kdtSessionCategory(kdtSessionDto.getKdtSessionCategory())
                    .kdtSessionMaxCapacity(kdtSessionDto.getKdtSessionMaxCapacity())
                    .kdtSessionThumbnail(kdtSessionDto.getKdtSessionThumbnail())
                    .kdtSessionStartTime(kdtSessionDto.getKdtSessionStartTime())
                    .kdtSessionEndTime(kdtSessionDto.getKdtSessionEndTime())
                    .kdtSessionPostcode(kdtSessionDto.getKdtSessionPostcode())
                    .kdtSessionAddress(kdtSessionDto.getKdtSessionAddress())
                    .kdtSessionAddressDetail(kdtSessionDto.getKdtSessionAddressDetail())
                    .kdtSessionOnline(kdtSessionDto.getKdtSessionOnline())
                    .kdtSessionTotalDay(kdtSessionDto.getKdtSessionTotalDay())  // 추가된 필드
                    .kdtSessionOnedayTime(kdtSessionDto.getKdtSessionOnedayTime())  // 추가된 필드
                    .kdtSessionTotalTime(kdtSessionDto.getKdtSessionTotalTime())  // 추가된 필드
                    .build();

// 세션 엔티티 저장
            kdtSessionRepository.save(kdtSessionEntity);

            return 1; // 성공적으로 저장된 경우
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 예외 발생 시 실패
        }
    }



    //KDT 교육과정명 전체찾는 메서드임 =  courseall  중복해서 사용하면 댐
    @Override
    public List<KDTCourseDTO> courseall() {
        List<KDTCourseEntity> courses = kdtCourseRepository.findAll();
        List<KDTCourseDTO> courseall = courses.stream()
                .map(course -> new KDTCourseDTO(
                        course.getKdtCourseId(),
                        course.getKdtCourseTitle(),
                        course.getKdtCourseStatus(),
                        course.getKdtCourseType(),
                        course.getKdtCourseCreatedAt(),
                        course.getKdtCourseUpdatedAt()
                ))
                .collect(Collectors.toList());
        return courseall;
    }


    //과정명 선택화면 회차 찾고 과정명 찾아옴
    @Override
    public Map<String, Object> getSessionNumAndCourseTitleByCourseId(Long courseId) {
        try {
            // courseId에 해당하는 마지막 세션을 찾기 (세션 번호가 큰 순서대로)
            Optional<KDTSessionEntity> lastSession = kdtSessionRepository
                    .findTopByKdtCourseEntity_KdtCourseIdOrderByKdtSessionNumDesc(courseId);

            return lastSession.map(session -> {
                Map<String, Object> result = new HashMap<>();
                result.put("sessionNum", session.getKdtSessionNum());  // 세션 번호
                result.put("courseTitle", session.getKdtCourseEntity().getKdtCourseTitle());  // 과정명
                return result;
            }).orElseGet(() -> {
                // 세션이 없으면 courseId로 과정명을 찾아서 반환
                Optional<KDTCourseEntity> course = kdtCourseRepository.findById(courseId);
                Map<String, Object> result = new HashMap<>();
                result.put("sessionNum", 0);  // 세션 번호는 0
                result.put("courseTitle", course.map(KDTCourseEntity::getKdtCourseTitle).orElse("과정명 없음"));  // 과정명
                return result;
            });
        } catch (Exception e) {
            // 예외 발생 시 로깅
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("sessionNum", 0);  // 예외가 발생한 경우 기본값 0 반환
            result.put("courseTitle", "없음");  // 기본 과정명
            return result;
        }
    }

    //국비과정 수정 메서드
    @Override
    public boolean updateCourse(Long courseId, KDTCourseDTO kdtcourseDto) {
        Optional<KDTCourseEntity> optionalCourse = kdtCourseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            KDTCourseEntity course = optionalCourse.get();
            course.update(kdtcourseDto);
            kdtCourseRepository.save(course);

            return true;
        } else {
            return false;
        }

    }

    // 국비과정 프라이머키로 찾기
    @Override
    public KDTCourseDTO kdtcourseupdate(Long courseId) {
        // 1. courseId에 해당하는 과정 조회
        Optional<KDTCourseEntity> optionalCourse = kdtCourseRepository.findById(courseId);

        // 과정이 존재하지 않으면 예외 처리
        if (!optionalCourse.isPresent()) {
            throw new RuntimeException("해당 과정이 존재하지 않습니다.");
        }
        // Optional을 사용하여 DTO로 변환 후 변수에 담기
        KDTCourseDTO dto = optionalCourse
                .map(entity -> new KDTCourseDTO(
                        entity.getKdtCourseId(),
                        entity.getKdtCourseTitle(),
                        entity.getKdtCourseStatus(),
                        entity.getKdtCourseType(),
                        entity.getKdtCourseCreatedAt(),
                        entity.getKdtCourseUpdatedAt()
                ))
                .orElseThrow(() -> new RuntimeException("해당 과정이 존재하지 않습니다.")); // Optional이 비어 있을 경우 예외 처리

        // 변수를 리턴
        return dto;
    }

// 국비과정 삭제하는 메서드
@Override
public boolean deleteCourse(Long courseId) {
    Optional<KDTCourseEntity> courseOptional = kdtCourseRepository.findById(courseId);

    if (courseOptional.isEmpty()) {
        return false; // 과정이 존재하지 않으면 삭제할 수 없음
    }

    // 과정에 연관된 세션이 있다면 삭제 불가
    boolean hasSessions = kdtSessionRepository.existsByKdtCourseEntity_KdtCourseId(courseId);

    if (hasSessions) {
        return false; // 세션이 존재하면 삭제할 수 없음
    }

    // 세션이 없으면 삭제
    KDTCourseEntity course = courseOptional.get();
    kdtCourseRepository.delete(course);

    return true; // 삭제 성공
}

    // 국비과정 프라이머리키로 모든 세션 아이디를 찾아오는 메서드임
    @Override
    public boolean hasSessions(Long courseId) {
        return kdtSessionRepository.existsByKdtCourseEntity_KdtCourseId(courseId); // 세션이 있으면 true 반환
    }

    @Override
    public List<KDTSessionDTO> getSessionsByCourseId(Long courseId) {
        // courseId에 해당하는 세션 데이터를 DB에서 가져옴
        List<KDTSessionEntity> sessionEntities = kdtSessionRepository.findByKdtCourseEntity_KdtCourseId(courseId);

        // 가져온 세션 엔티티 리스트를 DTO로 변환
        List<KDTSessionDTO> sessionAll = sessionEntities.stream()
                .map(entity -> {
                    KDTSessionDTO dto = new KDTSessionDTO();
                    dto.setKdtSessionId(entity.getKdtSessionId());
                    dto.setKdtCourseId(entity.getKdtCourseEntity().getKdtCourseId());
                    dto.setKdtSessionNum(entity.getKdtSessionNum());
                    dto.setKdtSessionTitle(entity.getKdtSessionTitle());
                    dto.setKdtSessionDescript(entity.getKdtSessionDescript());
                    dto.setKdtSessionStartDate(entity.getKdtSessionStartDate());
                    dto.setKdtSessionEndDate(entity.getKdtSessionEndDate());
                    dto.setKdtSessionCategory(entity.getKdtSessionCategory());
                    dto.setKdtSessionMaxCapacity(entity.getKdtSessionMaxCapacity());
                    dto.setKdtSessionThumbnail(entity.getKdtSessionThumbnail());
                    dto.setKdtSessionStartTime(entity.getKdtSessionStartTime());
                    dto.setKdtSessionEndTime(entity.getKdtSessionEndTime());
                    dto.setKdtSessionPostcode(entity.getKdtSessionPostcode());
                    dto.setKdtSessionAddress(entity.getKdtSessionAddress());
                    dto.setKdtSessionAddressDetail(entity.getKdtSessionAddressDetail());
                    dto.setKdtSessionOnline(entity.getKdtSessionOnline());
                    dto.setKdtSessionTotalDay(entity.getKdtSessionTotalDay());
                    dto.setKdtSessionOnedayTime(entity.getKdtSessionOnedayTime());
                    dto.setKdtSessionTotalTime(entity.getKdtSessionTotalTime());
                    return dto;
                })
                .collect(Collectors.toList());  // DTO 리스트로 변환 후 반환

        // 변환된 sessionAll 반환
        return sessionAll;
    }

    //세션아이디로 단일객체 정보 가져오기
    @Override
    public KDTSessionDTO getSessionsBySessId(Long sessionId) {
        // sessionId로 KDTSessionEntity를 조회하고 Optional로 감싸 반환
        Optional<KDTSessionEntity> entityOpt = kdtSessionRepository.findById(sessionId);

        // 엔티티가 없다면 null을 반환
        if (!entityOpt.isPresent()) {
            return null;
        }

        KDTSessionEntity entity = entityOpt.get(); // 엔티티 객체 가져오기

        // KDTSessionDTO 객체 생성
        KDTSessionDTO dto = new KDTSessionDTO();

        // 엔티티 데이터를 DTO로 매핑
        dto.setKdtSessionId(entity.getKdtSessionId());
        dto.setKdtCourseId(entity.getKdtCourseEntity().getKdtCourseId());
        dto.setKdtSessionNum(entity.getKdtSessionNum());
        dto.setKdtSessionTitle(entity.getKdtSessionTitle());
        dto.setKdtSessionDescript(entity.getKdtSessionDescript());
        dto.setKdtSessionStartDate(entity.getKdtSessionStartDate());
        dto.setKdtSessionEndDate(entity.getKdtSessionEndDate());
        dto.setKdtSessionCategory(entity.getKdtSessionCategory());
        dto.setKdtSessionMaxCapacity(entity.getKdtSessionMaxCapacity());
        dto.setKdtSessionThumbnail(entity.getKdtSessionThumbnail());
        dto.setKdtSessionStartTime(entity.getKdtSessionStartTime());
        dto.setKdtSessionEndTime(entity.getKdtSessionEndTime());
        dto.setKdtSessionPostcode(entity.getKdtSessionPostcode());
        dto.setKdtSessionAddress(entity.getKdtSessionAddress());
        dto.setKdtSessionAddressDetail(entity.getKdtSessionAddressDetail());
        dto.setKdtSessionOnline(entity.getKdtSessionOnline());
        dto.setKdtSessionTotalDay(entity.getKdtSessionTotalDay());
        dto.setKdtSessionOnedayTime(entity.getKdtSessionOnedayTime());
        dto.setKdtSessionTotalTime(entity.getKdtSessionTotalTime());

        return dto;
    }

    @Override
    public boolean deleteInstructor(Long kdtSessionId, Long userId) {
        // 세션 ID와 사용자 ID로 강사를 찾기
        KDTStaffEntity kdtStaffEntity = kdtStaffRepository.findByUserEntityUserIdAndKdtSessionEntityKdtSessionId(userId, kdtSessionId)
                .orElse(null); // 강사를 찾을 수 없으면 null 반환

        if (kdtStaffEntity != null) {
            // 강사 삭제
            kdtStaffRepository.delete(kdtStaffEntity);
            return true;
        } else {
            // 강사를 찾을 수 없으면 false 반환
            return false;
        }
    }

}
