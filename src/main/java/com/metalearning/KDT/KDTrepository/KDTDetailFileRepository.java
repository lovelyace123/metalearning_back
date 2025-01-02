package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTDetailFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTDetailFileRepository extends JpaRepository<KDTDetailFileEntity,Long> {
}
