package com.metalearning.KDT.KDTrepository;

import com.metalearning.KDT.KDTentity.KDTStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDTStaffRepository extends JpaRepository<KDTStaffEntity,Long> {

}
