package com.metalearning.KDT.KDTrepository;


import com.metalearning.KDT.KDTentity.KDTCourseVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTCourseVideoRepository extends JpaRepository<KDTCourseVideoEntity,Long> {

}
