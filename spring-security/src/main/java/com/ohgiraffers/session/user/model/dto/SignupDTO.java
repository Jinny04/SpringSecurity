package com.ohgiraffers.session.user.model.dto;

/* 설명. 회원가입 form 데이터를 한 번에 편하게 처리하기 위한 커맨드 객체 */
public class SignupDTO {

    private String username;    // 사용자 로그인 ID
    private String password;    // 사용자 로그인 PW
    private String fullName;    // 사용자 이름
    private String role;        // 사용자 권한

    public SignupDTO() {
    }

    public SignupDTO(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignupDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
