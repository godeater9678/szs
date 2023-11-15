package com.szs.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password; // 암호화된 비밀번호를 저장
    private String name;
    private String regNo; // 암호화된 주민등록번호를 저장

    // 생성자, getter 및 setter 메서드 생략

    // 기타 필요한 메서드 추가
}