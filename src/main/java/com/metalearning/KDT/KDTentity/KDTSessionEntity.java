package com.metalearning.KDT.KDTentity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "KDT_session")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_session_id")
    private Long kdtSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_course_id", referencedColumnName = "KDT_course_id", nullable = false)
    private KDTCourseEntity kdtCourseEntity;

    @Column(name = "KDT_session_num", nullable = false)
    private int kdtSessionNum;  //회차 번호 시작은 1번

    @Column(name = "KDT_session_title", nullable = false)
    private String kdtSessionTitle;     // 회차 명(회차명과 다를 수도 있고 같을 수도 있음)

    @Column(name = "KDT_session_descript")
    private String kdtSessionDescript;  // 회차 간단 설명

    @Column(name = "KDT_session_start_date")
    private LocalDate kdtSessionStartDate;  // 회차 시작일

    @Column(name = "KDT_session_end_date")
    private LocalDate kdtSessionEndDate;    // 회차 종료일

    @Column(name = "KDT_session_category")
    private String kdtSessionCategory;      // 회차 카테고리

    @Column(name = "KDT_session_max_capacity")
    private int kdtSessionMaxCapacity;      // 회차 최대인원

    @Column(name = "KDT_session_thumbnail")
    private String kdtSessionThumbnail;     // 회차 썸네일

    @Column(name = "KDT_session_start_time")
    private LocalTime kdtSessionStartTime;  // 회차 시작시간

    @Column(name = "KDT_session_end_time")
    private LocalTime kdtSessionEndTime;    // 회차 종료시간

    @Column(name = "KDT_session_postcode")
    private String kdtSessionPostcode;      //회차 우편번호

    @Column(name = "KDT_session_address")
    private String kdtSessionAddress;       // 회차 주소

    @Column(name = "KDT_session_address_detail")
    private String kdtSessionAddressDetail; // 회차 상세 주소

    @Column(name = "KDT_session_online")
    private Boolean kdtSessionOnline;       // 온라인 여부
}
