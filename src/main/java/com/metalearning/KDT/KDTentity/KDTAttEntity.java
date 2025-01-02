package com.metalearning.KDT.KDTentity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "KDT_att")
@Getter
@EntityListeners(AuditingEntityListener.class)  // 생성일자와 수정일자를 자동으로 관리하는 리스너
public class KDTAttEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_att_id")
    private Long KDTAttId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_part_id", referencedColumnName = "KDT_part_id", nullable = false)
    private KDTPartEntity kdtPartEntity;  // 국비 참가자 id

    @CreatedDate
    @Column(name = "KDT_att_date", nullable = false, updatable = false)
    private LocalDate KDTAttDate;   // 출석부 일자

    @Column(name = "KDT_att_entry_time")
    private LocalDateTime KDTAttEntryTime;  //출석부 입실 시간

    @Column(name = "KDT_att_exit_time")
    private LocalDateTime KDTAttExitTime;   //출석부 퇴실 시간

    @Column(name = "KDT_att_leave_start")
    private LocalDateTime KDTAttLeaveStart; //출석부 외출 시작 시간

    @Column(name = "KDT_att_leave_end")
    private LocalDateTime KDTAttLeaveEnd;   //출석부 외출 종료 시간

    @Enumerated(EnumType.STRING)
    @Column(name = "KDT_att_status")
    private KDTAttStatus KDTAttStatus;

    //상태를 업데이트 하는 함수 추가
    //1. 입실o 외출x 외출x 퇴실x -> 입실
    //2. 입실o 외출o 외출x 퇴실x -> 외출
    //3. 입실o 외출o 외출o 퇴실x -> 입실
    //4. 입실o 외출? 외출? 퇴실o -> 퇴실or조퇴
    //
    //5.입실o 외출? 외출? 퇴실x 후 다음날
    //결석 처리
}
