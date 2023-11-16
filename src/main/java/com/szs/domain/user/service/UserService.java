package com.szs.domain.user.service;

import com.szs.domain.user.entity.SzsUser;

public interface UserService {
    void saveUser(SzsUser szsUser);
    boolean isValidPassword(String userid, String password);
}
