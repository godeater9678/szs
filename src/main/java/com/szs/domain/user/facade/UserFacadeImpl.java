package com.szs.domain.user.facade;

import com.szs.domain.user.entity.SzsUser;
import com.szs.domain.user.exception.SzsBadRequestException;
import com.szs.domain.user.helper.JwtUtil;
import com.szs.domain.user.service.UserAllowedService;
import com.szs.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;




@Service
public class UserFacadeImpl implements UserFacade {

    UserAllowedService userAllowedService;
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

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

    @Override
    public String login(String userId, String password) throws Exception {
        if(userService.isValidPassword(userId, password)){
            return genToken(userId);
        }else {
            throw new SzsBadRequestException("아이디 암호가 일치하지 않습니다.");
        }
    }

    @Override
    public String genToken(String userId) {

        return jwtUtil.generateToken(userId);
    }

    @Override
    public SzsUser getUserByToken(String token) throws Exception {
        String userId = jwtUtil.extractUsername(token);
        var szsUser = userService.getUserByUserId(userId);
        szsUser.setPassword(null);

        return szsUser;
    }


}
