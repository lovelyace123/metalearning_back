package com.metalearning.KDT.KDTentity;

import com.metalearning.user.userentity.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "KDT_staff")
@Getter
public class KDTStaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_staff_id")  // 국비 담당자 명단 id
    private Long KDTStaffId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_session_id", referencedColumnName = "KDT_session_id", nullable = false)
    private KDTSessionEntity kdtSessionEntity;  // 국비회차id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;  // 국비 참가 담당자 user_id()
}
