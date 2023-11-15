package com.szs.repository;

import com.szs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 사용자 정의 쿼리 메서드를 여기에 정의할 수 있음
}