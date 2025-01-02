package com.metalearning.KDT.KDTentity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KDTAttStatus {
    ARRIVAL("입실"),
    DEPARTURE("퇴실"),
    OUTGOING("외출"),
    EARLY_LEAVE("조퇴"),
    VACATION("휴가"),
    ABSENT("결석"),
    TARDY("지각"),
    SICK_LEAVE("병결");

    private final String text;
}
