package com.metalearning.KDT.KDTentity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "KDT_instr_eval")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTInstrEvalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_instr_eval_id")
    private Long kdtInstrEvalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_staff_id", referencedColumnName = "KDT_staff_id", nullable = false)
    private KDTStaffEntity kdtStaffEntity; // 강사

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_part_id", referencedColumnName = "KDT_part_id", nullable = false)
    private KDTPartEntity kdtPartEntity;  // 국비 참가자 id

    @Column(name = "KDT_instr_eval_rating", nullable = false)
    private Integer kdtInstrEvalRating; // 평가 점수 (예: 1~5)

    @Column(name = "KDT_instr_eval_review", nullable = false)
    private String kdtInstrEvalReview; // 평가 내용

    @CreatedDate
    @Column(name = "KDT_instr_eval_created_at", updatable = false, nullable = false)
    private LocalDateTime kdtInstrEvalCreatedAt;  // 국비강사 평가 생성일자

    @LastModifiedDate
    @Column(name = "KDT_instr_eval_updated_at")
    private LocalDateTime kdtInstrEvalUpdatedAt;  // 국비강사 평가 수정일자
}
