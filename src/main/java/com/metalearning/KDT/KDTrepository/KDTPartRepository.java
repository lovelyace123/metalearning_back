package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTPartRepository extends JpaRepository<KDTPartEntity,Long> {

}
