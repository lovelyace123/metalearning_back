package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTSessionEvalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTSessionEvalRepository extends JpaRepository<KDTSessionEvalEntity,Long> {

}
