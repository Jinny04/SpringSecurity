-- 0) DBMS 접속 및 SQL 쿼리를 실행할 타겟 스키마(securitydb) 선택
    -- ID : springsecurity
    -- PW : springsecurity
    -- Default Schema : securitydb
-- USE securitydb;

-- 1) 테이블 생성
DROP TABLE IF EXISTS tbl_user CASCADE;

-- tbl_user 테이블 생성
CREATE TABLE IF NOT EXISTS tbl_user
(
    user_code INT AUTO_INCREMENT COMMENT '사용자 식별코드',
    username VARCHAR(30) NOT NULL COMMENT '사용자 아이디',
    password VARCHAR(100) NOT NULL COMMENT '사용자 비밀번호',
    full_name VARCHAR(30) COMMENT '사용자 이름',
    user_role VARCHAR(50) NOT NULL COMMENT '사용자 권한',
    CONSTRAINT pk_category_code PRIMARY KEY (user_code)
) ENGINE=INNODB COMMENT '사용자정보';

-- 2) 생성 결과 조회
-- SHOW TABLES;