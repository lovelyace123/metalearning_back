package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KDTSessionRepository extends JpaRepository <KDTSessionEntity, Long>{

    // 세션 번호로 중복 체크
    boolean existsByKDTSessionNum(Integer sessionNum);

    // 'KDTCourseEntity'의 'KDTCourseId'를 기준으로 최신 세션 번호를 조회
    Optional<KDTSessionEntity> findTopByKdtCourseEntity_KDTCourseIdOrderByKDTSessionNumDesc(Long courseId);

    // courseId로 세션을 찾는 메서드 정의
    List<KDTSessionEntity> findByKdtCourseEntity_KDTCourseId(Long courseId);


}
