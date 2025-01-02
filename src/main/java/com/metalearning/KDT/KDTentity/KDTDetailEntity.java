package com.metalearning.KDT.KDTentity;

import com.metalearning.user.userentity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "KDT_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTDetailEntity { // 홍보글 상세내용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_detail_id")
    private Long kdtDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_session_id", referencedColumnName = "KDT_session_id", nullable = false)
    private KDTSessionEntity kdtSessionEntity;  // 국비회차id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;              // 국비 담당자 user_id()

    @Column(name = "KDT_detail_content", nullable = false)
    private String kdtDetailContent;            // 상세 내용

    @CreatedDate
    @Column(name = "KDT_detail_created_at", nullable = false, updatable = false)
    private LocalDateTime kdtDetailCreatedAt;   // 상세 작성일

    @LastModifiedDate
    @Column(name = "KDT_detail_updated_at")
    private LocalDateTime kdtDetailUpdatedAt;   // 상세 수정일

}
