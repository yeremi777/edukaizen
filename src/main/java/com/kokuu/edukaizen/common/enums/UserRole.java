package com.kokuu.edukaizen.common.enums;

public enum UserRole {
    Admin(1),
    Instructor(2),
    Student(3);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
