package com.szs.domain.user.service;

import com.szs.domain.user.repository.repository.UserRepository;
import com.szs.domain.user.entity.SzsUser;
import com.szs.domain.user.helper.ShaEnc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(SzsUser szsUser) {
        // 비밀번호 암호화
        String encryptedPassword = ShaEnc.hashPassword(szsUser.getPassword());
        szsUser.setPassword(encryptedPassword);

        // 주민등록번호 암호화
        String encryptedRegNo = ShaEnc.hashPassword(szsUser.getRegNo());
        szsUser.setRegNo(encryptedRegNo);

        userRepository.save(szsUser);
    }

}
