package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTCourseRepository extends JpaRepository <KDTCourseEntity,Long>{

    boolean existsByKDTCourseTitle(String KDTCourseTitle);

}
