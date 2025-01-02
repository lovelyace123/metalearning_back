package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTInstrEvalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTInstrEvalRepository extends JpaRepository<KDTInstrEvalEntity,Long> {

}
