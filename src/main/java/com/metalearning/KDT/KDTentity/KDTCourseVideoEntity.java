package com.metalearning.KDT.KDTentity;

import com.metalearning.user.userentity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "KDT_course_video")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTCourseVideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_course_video_id")
    private Long kdtCourseVideoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_course_outline_id", referencedColumnName ="KDT_course_outline_id", nullable = false)
    private KDTCourseOutlineEntity kdtCourseOutlineEntity; // KDT_course_outline 테이블과 연관

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;  // 관리자, 매니저

    @Column(name = "KDT_course_video_category", nullable = false)
    private String kdtCourseVideoCategory;

    @Column(name = "KDT_course_video_title", nullable = false)
    private String kdtCourseVideoTitle;

    @Column(name = "KDT_course_video_file", nullable = false)
    private String kdtCourseVideoFile;

    @Column(name = "KDT_course_video_UUID", nullable = false)
    private String kdtCourseVideoUUID; // URL 링크도 여기에 포함됨

    @Column(name = "KDT_course_video_size", nullable = false)
    private Long kdtCourseVideoSize;

    @CreatedDate
    @Column(name = "KDT_course_video_created_at", nullable = false, updatable = false)
    private LocalDateTime kdtCourseVideoCreatedAt; // 동영상 업로드 시간

    @Column(name = "KDT_course_video_type", nullable = false)
    private String kdtCourseVideoType;

    @Column(name = "KDT_course_video_time", nullable = false)
    private Long kdtCourseVideoTime; // 동영상 시간 (초 단위 등)
}
