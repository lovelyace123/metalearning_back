package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTCourseOutlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTCourseOutlineRepository extends JpaRepository<KDTCourseOutlineEntity,Long> {

}
