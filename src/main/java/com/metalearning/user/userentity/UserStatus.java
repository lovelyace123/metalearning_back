package com.metalearning.user.userentity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    BANNED("차단");

    private final String text;
}