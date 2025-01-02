package com.metalearning.KDT.KDTentity;


import com.metalearning.KDT.KDTdto.KDTCourseDto;
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
    @Column(name = "KDT_course_id")
    private Long KDTCourseId;

    @Column(name = "KDT_course_title", nullable = false) // 과정명
    private String KDTCourseTitle;

    @Column(name = "KDT_course_status", nullable = false)
    private Boolean KDTCourseStatus;

    @Column(name = "KDT_course_type", nullable = false)
    private String KDTCourseType;

    @CreatedDate
    @Column(name = "KDT_course_created_at", updatable = false)
    private LocalDateTime KDTCourseCreatedAt; // 생성 날짜

    @LastModifiedDate
    @Column(name = "KDT_course_updated_at")
    private LocalDateTime KDTCourseUpdatedAt; // 마지막 수정 날짜


    // 업데이트 메서드
    public void update(KDTCourseDto kdtcourseDto) {
        if (kdtcourseDto.getKDTCourseTitle() != null) {
            this.KDTCourseTitle = kdtcourseDto.getKDTCourseTitle();
        }
        if (kdtcourseDto.getKDTCourseStatus() != null) {
            this.KDTCourseStatus = kdtcourseDto.getKDTCourseStatus();
        }
        if (kdtcourseDto.getKDTCourseType() != null) {
            this.KDTCourseType = kdtcourseDto.getKDTCourseType();
        }
        this.KDTCourseUpdatedAt = LocalDateTime.now(); // 항상 현재 시간으로 수정일 갱신
    }


}
