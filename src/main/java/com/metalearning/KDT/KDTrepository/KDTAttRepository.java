package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTAttEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTAttRepository extends JpaRepository<KDTAttEntity,Long> {

}
