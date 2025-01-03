package com.metalearning.KDT.KDTdto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KDTStaffDTO {
    private Long kdtStaffId;
    private Long kdtSessionId;
    private List<Long> userIds;  // 여러 강사 ID를 받을 수 있도록 List 사용
    private Long userId;
}
