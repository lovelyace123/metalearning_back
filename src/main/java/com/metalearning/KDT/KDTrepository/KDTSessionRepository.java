package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KDTSessionRepository extends JpaRepository <KDTSessionEntity, Long>{

    // 코스 ID와 세션 번호가 존재하는지 확인
    boolean existsByKdtCourseEntity_KdtCourseIdAndKdtSessionNum(Long kdtCourseId, int kdtSessionNum);

    // 'kdtCourseEntity'의 'kdtCourseId'를 기준으로 최신 세션 번호를 조회
    Optional<KDTSessionEntity> findTopByKdtCourseEntity_KdtCourseIdOrderByKdtSessionNumDesc(Long courseId);

    // 주어진 courseId에 대해 세션이 존재하는지 확인하는 메서드
    boolean existsByKdtCourseEntity_KdtCourseId(Long courseId);

    // courseId에 해당하는 세션 리스트를 가져오는 메서드
    List<KDTSessionEntity> findByKdtCourseEntity_KdtCourseId(Long courseId);

}
