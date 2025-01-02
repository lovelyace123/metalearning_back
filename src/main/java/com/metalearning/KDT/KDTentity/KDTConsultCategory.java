package com.metalearning.KDT.KDTentity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KDTConsultCategory {
    CAREER("진로"),
    CONCERN("고민"),
    SUGGESTION("건의");

    private final String text;
}
