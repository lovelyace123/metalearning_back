package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTConsultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTConsultRepository extends JpaRepository<KDTConsultEntity,Long> {
}
