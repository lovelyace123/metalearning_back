package com.metalearning.user.userrepository;

import com.metalearning.user.userentity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String userEmail);


    // userRole이 'MANAGER'인 사용자만 찾는 메서드 추가
    List<UserEntity> findByUserRole(String userRole);


}
