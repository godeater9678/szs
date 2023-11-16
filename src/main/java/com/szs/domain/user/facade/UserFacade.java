package com.szs.domain.user.facade;

import com.szs.domain.user.dto.scrap.ScrapResponse;
import com.szs.domain.user.dto.UserDto;
import com.szs.domain.user.dto.scrap.TaxRefund;
import com.szs.domain.user.entity.SzsUser;

public interface UserFacade {

    public void saveUser(SzsUser szsUser) throws Exception;
    public boolean isAllowedUser(String name, String regNo);
    public String login(String userId, String password) throws Exception;
    String genToken(String userId);
    SzsUser getUserByToken(String token) throws Exception;

    ScrapResponse getScrap(UserDto userDto) throws Exception;
    TaxRefund getRefund(UserDto userDto) throws Exception;
}
