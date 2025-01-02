package com.metalearning.KDT.KDTservice;

import com.metalearning.KDT.KDTdto.KDTCourseDto;
import com.metalearning.KDT.KDTdto.KDTSessionDto;
import com.metalearning.KDT.KDTentity.KDTCourseEntity;
import com.metalearning.KDT.KDTentity.KDTSessionEntity;
import com.metalearning.KDT.KDTrepository.KDTCourseRepository;
import com.metalearning.KDT.KDTrepository.KDTSessionRepository;
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


    //국비과정 등록하는 메서드임
    @Override
    public int kdtcoursesave(KDTCourseDto kdtCourseDto) {
        try {
            // 중복 확인 사용하는거임
            boolean exists = kdtCourseRepository.existsByKDTCourseTitle(kdtCourseDto.getKDTCourseTitle());
            if (exists) {
                return 2;
            }
            KDTCourseEntity kdtCourseEntity = KDTCourseEntity.builder()
                    .KDTCourseTitle(kdtCourseDto.getKDTCourseTitle())
                    .KDTCourseStatus(kdtCourseDto.getKDTCourseStatus())
                    .KDTCourseType(kdtCourseDto.getKDTCourseType())
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
    public int kdtsessionsave(KDTSessionDto kdtSessionDto) {
        try {
            boolean exists = kdtSessionRepository.existsByKDTSessionNum(kdtSessionDto.getKDTSessionNum());

            if (exists) {
                return 2;
            }
            KDTSessionEntity kdtSessionEntity = KDTSessionEntity.builder()
                    .kdtCourseEntity(KDTCourseEntity.builder().KDTCourseId(kdtSessionDto.getKDTCourseId()).build()) // 관계 설정
                    .KDTSessionNum(kdtSessionDto.getKDTSessionNum())
                    .KDTSessionTitle(kdtSessionDto.getKDTSessionTitle())
                    .KDTSessionDescript(kdtSessionDto.getKDTSessionDescript())
                    .KDTSessionStartDate(kdtSessionDto.getKDTSessionStartDate())
                    .KDTSessionEndDate(kdtSessionDto.getKDTSessionEndDate())
                    .KDTSessionCategory(kdtSessionDto.getKDTSessionCategory())
                    .KDTSessionMaxCapacity(kdtSessionDto.getKDTSessionMaxCapacity())
                    .KDTSessionThumbnail(kdtSessionDto.getKDTSessionThumbnail())
//                    .KDTSessionStartTime(kdtSessionDto.getKDTSessionStartTime())
//                    .KDTSessionEndTime(kdtSessionDto.getKDTSessionEndTime())
                    .KDTSessionPostcode(kdtSessionDto.getKDTSessionPostcode())
                    .KDTSessionAddress(kdtSessionDto.getKDTSessionAddress())
                    .KDTSessionAddressDetail(kdtSessionDto.getKDTSessionAddressDetail())
                    .KDTSessionOnline(kdtSessionDto.getKDTSessionOnline())
                    .build();

            kdtSessionRepository.save(kdtSessionEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    //KDT 교육과정명 전체찾는 메서드임 =  courseall  중복해서 사용하면 댐
    @Override
    public List<KDTCourseDto> courseall() {
        List<KDTCourseEntity> courses = kdtCourseRepository.findAll();
        List<KDTCourseDto> courseDtos = courses.stream()
                .map(course -> new KDTCourseDto(
                        course.getKDTCourseId(),
                        course.getKDTCourseTitle(),
                        course.getKDTCourseStatus(),
                        course.getKDTCourseType(),
                        course.getKDTCourseCreatedAt(),
                        course.getKDTCourseUpdatedAt()
                ))
                .collect(Collectors.toList());
        return courseDtos;
    }


    //과정명 선택화면 회차 찾고 과정명 찾아옴
    @Override
    public Map<String, Object> getSessionNumAndCourseTitleByCourseId(Long courseId) {
        try {
            // courseId에 해당하는 마지막 세션을 찾기 (세션 번호가 큰 순서대로)
            Optional<KDTSessionEntity> lastSession = kdtSessionRepository
                    .findTopByKdtCourseEntity_KDTCourseIdOrderByKDTSessionNumDesc(courseId);

            return lastSession.map(session -> {
                Map<String, Object> result = new HashMap<>();
                result.put("sessionNum", session.getKDTSessionNum());  // 세션 번호
                result.put("courseTitle", session.getKdtCourseEntity().getKDTCourseTitle());  // 과정명
                return result;
            }).orElseGet(() -> {
                // 세션이 없으면 courseId로 과정명을 찾아서 반환
                Optional<KDTCourseEntity> course = kdtCourseRepository.findById(courseId);
                Map<String, Object> result = new HashMap<>();
                result.put("sessionNum", 0);  // 세션 번호는 0
                result.put("courseTitle", course.map(KDTCourseEntity::getKDTCourseTitle).orElse("과정명 없음"));  // 과정명
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

    // 국비과정 프라이머키로 찾기
    @Override
    public KDTCourseDto kdtcourseupdate(Long courseId) {
        // 1. courseId에 해당하는 과정 조회
        Optional<KDTCourseEntity> optionalCourse = kdtCourseRepository.findById(courseId);

        // 과정이 존재하지 않으면 예외 처리
        if (!optionalCourse.isPresent()) {
            throw new RuntimeException("해당 과정이 존재하지 않습니다.");
        }
        // Optional을 사용하여 DTO로 변환 후 변수에 담기
        KDTCourseDto dto = optionalCourse
                .map(entity -> new KDTCourseDto(
                        entity.getKDTCourseId(),
                        entity.getKDTCourseTitle(),
                        entity.getKDTCourseStatus(),
                        entity.getKDTCourseType(),
                        entity.getKDTCourseCreatedAt(),
                        entity.getKDTCourseUpdatedAt()
                ))
                .orElseThrow(() -> new RuntimeException("해당 과정이 존재하지 않습니다.")); // Optional이 비어 있을 경우 예외 처리

        // 변수를 리턴
        return dto;
    }

    //국비과정 수정 메서드
    @Override
    public boolean updateCourse(Long courseId, KDTCourseDto kdtcourseDto) {
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

    //국비과정 삭제하는 메서드   @Transactional
        @Override
        @Transactional
        public boolean deleteCourse(Long courseId) {
            Optional<KDTCourseEntity> courseOptional = kdtCourseRepository.findById(courseId);

            if (courseOptional.isPresent()) {
                // 연관된 세션들을 가져오기
                List<KDTSessionEntity> sessions = kdtSessionRepository.findByKdtCourseEntity_KDTCourseId(courseId);

                // 세션에서 과정 참조를 null로 설정
                for (KDTSessionEntity session : sessions) {
                    // 세터 없이 필드 직접 수정 (필드가 final이 아니어야 함)
                    session.kdtCourseEntity = null;  // 필드를 직접 null로 설정
                    kdtSessionRepository.save(session);  // 업데이트된 세션 저장
                }

                // 과정을 삭제
                kdtCourseRepository.deleteById(courseId);
                return true;
            } else {
                return false;
            }
        }




}