package com.ohgiraffers.session.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 설명. config 패키지 생성 후, 설정 관련 클래스는 해당 패키지에 몰아넣을 것이다.
 *  아래 @SpringBootApplication 같은 경우, Component Scan 기능의 디폴트 스캔 경로가 이동되었기 때문에
 *  scanBasePackages를 별도로 지정해줘야 한다.
 * */
@SpringBootApplication(scanBasePackages = "com.ohgiraffers.session")
@MapperScan(basePackages = "com.ohgiraffers.session", annotationClass = Mapper.class)
public class Chap01SessionEasyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap01SessionEasyApplication.class, args);
    }

}
