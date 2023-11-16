package com.szs.domain.user.service;

import com.szs.domain.user.exception.SzsBadRequestException;
import com.szs.domain.user.helper.AesEnc;
import com.szs.domain.user.repository.UserRepository;
import com.szs.domain.user.entity.SzsUser;
import com.szs.domain.user.helper.ShaEnc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private AesEnc aesEnc;

    @Autowired
    UserServiceImpl(UserRepository userRepository, AesEnc aesEnc){
        this.userRepository = userRepository;
        this.aesEnc = aesEnc;
    }

    public void saveUser(SzsUser szsUser) throws Exception {
        // 비밀번호 암호화
        String encryptedPassword = ShaEnc.hashPassword(szsUser.getPassword());
        szsUser.setPassword(encryptedPassword);

        // 주민등록번호 암호화
        String encryptedRegNo = aesEnc.encrypt(szsUser.getRegNo());
        szsUser.setRegNo(encryptedRegNo);

        userRepository.save(szsUser);
    }

    public boolean isValidPassword(String userid, String password){
        return userRepository.findByUserIdAndPassword(userid, ShaEnc.hashPassword(password)) != null;
    }

    @Override
    public SzsUser getUserByUserId(String userId) throws Exception {
        SzsUser szsUser =  userRepository.findByUserId(userId);
        if(szsUser == null) {
            throw new SzsBadRequestException("존재하지 않는 사용자입니다.");
        }
        szsUser.setRegNo( aesEnc.decrypt(szsUser.getRegNo()));
        return szsUser;
    }

}
