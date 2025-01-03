package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTSessionEntity;
import com.metalearning.KDT.KDTentity.KDTStaffEntity;
import com.metalearning.user.userentity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KDTStaffRepository extends JpaRepository<KDTStaffEntity,Long> {
    // 세션과 사용자 조합이 이미 존재하는지 확인하는 메서드
    boolean existsByKdtSessionEntityAndUserEntity(KDTSessionEntity sessionEntity, UserEntity userEntity);

    // 세션 ID로 이미 등록된 강사들을 조회하는 메서드
    List<KDTStaffEntity> findByKdtSessionEntity_KdtSessionId(Long sessionId);

    // 세션 ID와 사용자 ID로 강사를 찾는 메서드
    Optional<KDTStaffEntity> findByUserEntityUserIdAndKdtSessionEntityKdtSessionId(Long userId, Long kdtSessionId);

}
