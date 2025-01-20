package com.kokuu.edukaizen.common.enums;

public enum UserRole {
    ADMIN(1),
    INSTRUCTOR(2),
    STUDENT(3);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
