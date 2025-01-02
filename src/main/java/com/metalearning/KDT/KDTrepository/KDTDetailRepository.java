package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTDetailRepository extends JpaRepository<KDTDetailEntity,Long> {

}
