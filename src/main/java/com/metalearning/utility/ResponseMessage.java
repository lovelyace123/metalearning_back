package com.metalearning.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseMessage {
    private String status;  // 상태 변수
    private String message; // 메시지 변수
}
