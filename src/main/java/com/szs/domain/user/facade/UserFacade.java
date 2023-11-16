package com.szs.domain.user.facade;

import com.szs.domain.user.entity.SzsUser;

public interface UserFacade {

    public void saveUser(SzsUser szsUser) throws Exception;
    public boolean isAllowedUser(String name, String regNo);
    public String login(String userId, String password) throws Exception;
    String genToken(String userId);
}
