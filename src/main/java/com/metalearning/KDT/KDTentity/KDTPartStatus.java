package com.metalearning.KDT.KDTentity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KDTPartStatus {
    WAITING("대기"),
    IN_PROGRESS("수료중"),
    DISMISSED("제적"),
    COMPLETED("수료완료");

    private final String text;
}
