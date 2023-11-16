package com.szs.domain.user.facade;

import com.szs.domain.user.entity.SzsUser;
import com.szs.domain.user.exception.SzsBadRequestException;
import com.szs.domain.user.service.UserAllowedService;
import com.szs.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacadeImpl implements UserFacade {

    UserAllowedService userAllowedService;
    UserService userService;

    @Autowired
    UserFacadeImpl(UserAllowedService userAllowedService, UserService userService){
        this.userAllowedService = userAllowedService;
        this.userService = userService;
    }

    @Override
    public void saveUser(SzsUser szsUser) throws Exception {
        if(userAllowedService.isAllowed(szsUser.getName(), szsUser.getRegNo())){
            userService.saveUser(szsUser);
        }else{
            throw new SzsBadRequestException("등록이 거부된 사용자입니다.");
        }
    }

    @Override
    public boolean isAllowedUser(String name, String regNo) {
        return false;
    }
}
