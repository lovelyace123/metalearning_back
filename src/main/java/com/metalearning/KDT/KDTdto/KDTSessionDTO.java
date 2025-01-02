package com.metalearning.KDT.KDTdto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString
public class KDTSessionDTO {

    private Long kdtSessionId;
    private Long kdtCourseId;  // 수정된 필드명 (Long 타입)
    private int kdtSessionNum;
    private String kdtSessionTitle;
    private String kdtSessionDescript;
    private LocalDate kdtSessionStartDate;
    private LocalDate kdtSessionEndDate;
    private String kdtSessionCategory;
    private int kdtSessionMaxCapacity;
    private String kdtSessionThumbnail;
    private LocalDateTime kdtSessionStartTime;
    private LocalDateTime kdtSessionEndTime;
    private String kdtSessionPostcode;
    private String kdtSessionAddress;
    private String kdtSessionAddressDetail;
    private Boolean kdtSessionOnline;


}
