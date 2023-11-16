package com.szs.domain.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "szs_user",
        uniqueConstraints = {@UniqueConstraint(name = "unique_user_id", columnNames = "userId"),
                @UniqueConstraint(name = "unique_reg_no", columnNames = "regNo"),
        })
@Data
public class SzsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String userId;
    @Column(length = 100)
    private String password; // 암호화된 비밀번호를 저장
    @Column(length = 20)
    private String name;
    @Column(length = 100)
    private String regNo; // 암호화된 주민등록번호를 저장


}