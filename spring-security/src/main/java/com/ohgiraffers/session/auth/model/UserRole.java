package com.ohgiraffers.session.auth.model;

public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");

    // 필드
    private String role;

    // 매개변수 있는 생성자
    UserRole(String role) {
        this.role = role;
    }

    // Getter(Setter는 없음에 주의!)
    public String getRole() {
        return role;
    }

    // toSting()
    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                '}';
    }
}
