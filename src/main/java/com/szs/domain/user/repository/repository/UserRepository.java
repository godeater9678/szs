package com.szs.domain.user.repository.repository;

import com.szs.domain.user.entity.SzsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SzsUser, Long> {

}