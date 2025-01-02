package com.metalearning.KDT.KDTentity;

import com.metalearning.user.userentity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "KDT_part")
@Getter
public class KDTPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_part_id")
    private Long kdtPartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_session_id", referencedColumnName = "KDT_session_id", nullable = false)
    private KDTSessionEntity kdtSessionEntity;  // 국비회차id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;  // 국비 참가 담당자 user_id()

    @Enumerated(EnumType.STRING)
    @Column(name = "KDT_part_status", nullable = false)
    private KDTPartStatus kdtPartStatus;   // 대기, 수료중, 제적, 수료완료

    @Column(name = "KDT_part_emp", nullable = false)
    private Boolean kdtPartEmp;     // 취직 여부 False:구직상태 / True:취직상태
}
