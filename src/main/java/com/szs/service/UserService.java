package com.szs.service;

import com.szs.model.User;
import com.szs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // 비밀번호 암호화
        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        // 주민등록번호 암호화
        String encryptedRegNo = encryptRegNo(user.getRegNo());
        user.setRegNo(encryptedRegNo);

        return userRepository.save(user);
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = md.digest(password.getBytes());

            StringBuilder hexPassword = new StringBuilder();
            for (byte b : passwordBytes) {
                hexPassword.append(String.format("%02x", b));
            }

            return hexPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 오류 발생", e);
        }
    }

    private String encryptRegNo(String regNo) {
        // 주민등록번호를 암호화하는 로직을 추가하세요.
        // 실제로는 더 안전한 방법으로 암호화해야 합니다.
        return regNo; // 간단한 예제로 주민등록번호를 그대로 반환
    }

}
