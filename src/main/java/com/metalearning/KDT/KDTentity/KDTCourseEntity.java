package com.metalearning.KDT.KDTentity;

import com.metalearning.KDT.KDTdto.KDTCourseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "KDT_course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_course_id") //국비 과정 id
    private Long kdtCourseId;

    @Column(name = "KDT_course_title", nullable = false) // 국비 과정명
    private String kdtCourseTitle;

    @Column(name = "KDT_course_status", nullable = false) // 국비과정 상태 활성화 비활성화
    private Boolean kdtCourseStatus;

    @Column(name = "KDT_course_type", nullable = false) //국비과정 타입 kdt
    private String kdtCourseType;

    @CreatedDate
    @Column(name = "KDT_course_created_at", updatable = false)
    private LocalDateTime kdtCourseCreatedAt; // 국비과정 생성 날짜

    @LastModifiedDate
    @Column(name = "KDT_course_updated_at")
    private LocalDateTime kdtCourseUpdatedAt; // 국비과정 수정 날짜

    // 업데이트 메서드
    public void update(KDTCourseDTO kdtCourseDto) {
        if (kdtCourseDto.getKdtCourseTitle() != null) {
            this.kdtCourseTitle = kdtCourseDto.getKdtCourseTitle();
        }
        if (kdtCourseDto.getKdtCourseStatus() != null) {
            this.kdtCourseStatus = kdtCourseDto.getKdtCourseStatus();
        }
        if (kdtCourseDto.getKdtCourseType() != null) {
            this.kdtCourseType = kdtCourseDto.getKdtCourseType();
        }
        this.kdtCourseUpdatedAt = LocalDateTime.now(); // 항상 현재 시간으로 수정일 갱신
    }
}
