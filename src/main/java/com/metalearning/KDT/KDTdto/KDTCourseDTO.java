package com.metalearning.KDT.KDTdto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KDTCourseDTO {

    private Long kdtCourseId;
    private String kdtCourseTitle;
    private Boolean kdtCourseStatus; // Boolean으로 수정
    private String kdtCourseType;
    private LocalDateTime kdtCourseCreatedAt; // 생성 날짜
    private LocalDateTime kdtCourseUpdatedAt; // 마지막 수정 날짜
}

