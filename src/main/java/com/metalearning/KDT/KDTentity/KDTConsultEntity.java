package com.metalearning.KDT.KDTentity;

import com.metalearning.user.userentity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "KDT_consult")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTConsultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_consult_id")
    private Long kdtConsultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;          // 국비 담당자 user_id() : admin 포함

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_part_id", referencedColumnName = "KDT_part_id", nullable = false)
    private KDTPartEntity kdtPartEntity;    // 국비 참가자 id

    @Column(name = "KDT_consult_title", nullable = false)
    private String kdtConsultTitle;         // 상담 제목

    @Column(name = "KDT_consult_content", nullable = false)
    private String kdtConsultContent;       // 상담 내용

    @Column(name = "KDT_consult_category", nullable = false)
    private KDTConsultCategory kdtConsultCategory;  // 상담 키테고리 예시 : 진로, 고민, 건의

    @CreatedDate
    @Column(name = "KDT_consult_date", nullable = false, updatable = false)
    private LocalDate kdtConsultDate;       // 상담 날짜
}
