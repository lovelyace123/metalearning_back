package com.metalearning.KDT.KDTentity;

import com.metalearning.user.userentity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "KDT_train")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTTrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_train_id")
    private Long kdtTrainId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;  // 작성자 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_session_id", referencedColumnName = "KDT_session_id", nullable = false)
    private KDTSessionEntity kdtSessionEntity;  // 국비회차id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_staff_id", referencedColumnName = "KDT_staff_id", nullable = false)
    private KDTStaffEntity kdtStaffEntity; // 강사

    @Column(name = "KDT_train_title", nullable = false)
    private String kdtTrainTitle;       // 훈련일지 제목

    @CreatedDate
    @Column(name = "KDT_train_date", nullable = false)
    private LocalDate kdtTrainDate;     // 훈련 날짜

    @Column(name = "KDT_train_content", nullable = false)
    private String kdtTrainContent;     // 내용

    @Column(name = "KDT_train_subject", nullable = false)
    private String kdtTrainSubject;     // 훈련일지 과목(파이썬, 자바, AI 등)
}
