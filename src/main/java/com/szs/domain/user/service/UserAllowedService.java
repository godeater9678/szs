package com.szs.domain.user.service;

import com.szs.domain.user.entity.SzsUser;
import com.szs.domain.user.entity.SzsUserAllowed;
import org.springframework.stereotype.Service;


public interface UserAllowedService {
    boolean isAllowed(String name, String regNo);
}
