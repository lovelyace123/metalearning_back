package com.metalearning.KDT.KDTentity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "KDT_course_outline")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTCourseOutlineEntity { //동영상 넣는 강의목차

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_course_outline_id")
    private Long kdtCourseOutlineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_session_id", referencedColumnName = "KDT_session_id", nullable = false)
    private KDTSessionEntity kdtSessionEntity;  // 국비회차id

    @Column(name = "KDT_course_outline", nullable = false)
    private String kdtCourseOutline;        // 국비강의 목차 String

}
