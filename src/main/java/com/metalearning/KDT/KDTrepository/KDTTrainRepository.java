package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTTrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTTrainRepository extends JpaRepository<KDTTrainEntity,Long> {

}
