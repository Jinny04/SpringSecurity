-- 0) 관리자 계정으로 DBMS 접속
    -- ID : root
    -- PW : qwer1234

-- 1) 디폴트 데이터베이스 스키마인 mysql로 이동
USE mysql;

-- 2) 데이터베이스 생성(securitydb)
CREATE DATABASE securitydb;
SHOW DATABASES;

-- 3) 유저 생성 (springsecurity/springsecurity)
CREATE USER 'springsecurity'@'%' IDENTIFIED BY 'springsecurity';
SELECT * FROM user;

-- 4) 유저에게 권한 부여
GRANT ALL PRIVILEGES ON securitydb.* TO 'springsecurity'@'%';
SHOW GRANTS FOR 'springsecurity'@'%';

-- 5) SQL을 실행할 타겟 스키마(securitydb)로 이동
USE securitydb;