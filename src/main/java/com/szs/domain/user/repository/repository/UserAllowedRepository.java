package com.szs.domain.user.repository.repository;

import com.szs.domain.user.entity.SzsUserAllowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAllowedRepository extends JpaRepository<SzsUserAllowed, Long> {

    List<SzsUserAllowed> findByNameAndRegNo(String name, String regNo);
}