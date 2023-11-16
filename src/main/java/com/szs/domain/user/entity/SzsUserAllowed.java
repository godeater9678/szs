package com.szs.domain.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "szs_user_allowed", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "regNo"})
})
@Data
public class SzsUserAllowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 100)
    private String regNo; // 암호화된 주민등록번호를 저장


}