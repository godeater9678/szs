package com.szs.domain.user.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ShaEnc {
    private static final int SALT_LENGTH = 16; // 솔트의 바이트 길이

    public static String hashPassword(String password) {
        try {
            // 고정된 솔트 값을 사용
            byte[] fixedSalt = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};

            // 솔트와 비밀번호를 합치기
            byte[] passwordWithSalt = new byte[fixedSalt.length + password.getBytes().length];
            System.arraycopy(fixedSalt, 0, passwordWithSalt, 0, fixedSalt.length);
            System.arraycopy(password.getBytes(), 0, passwordWithSalt, fixedSalt.length, password.getBytes().length);

            // SHA-256 해시 계산
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = md.digest(passwordWithSalt);

            // 해시 결과와 솔트를 함께 저장
            byte[] hashWithSalt = new byte[passwordBytes.length + fixedSalt.length];
            System.arraycopy(passwordBytes, 0, hashWithSalt, 0, passwordBytes.length);
            System.arraycopy(fixedSalt, 0, hashWithSalt, passwordBytes.length, fixedSalt.length);

            // 결과를 16진수로 변환하여 반환
            StringBuilder hexPassword = new StringBuilder();
            for (byte b : hashWithSalt) {
                hexPassword.append(String.format("%02x", b));
            }

            return hexPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 오류 발생", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        try {
            // 저장된 해시와 솔트를 분리
            byte[] hashWithSalt = new byte[storedPassword.length() / 2];
            for (int i = 0; i < storedPassword.length(); i += 2) {
                String hexByte = storedPassword.substring(i, i + 2);
                hashWithSalt[i / 2] = (byte) Integer.parseInt(hexByte, 16);
            }

            // 입력된 비밀번호와 솔트를 합치기
            byte[] inputPasswordWithSalt = new byte[hashWithSalt.length + inputPassword.getBytes().length];
            System.arraycopy(hashWithSalt, 0, inputPasswordWithSalt, 0, hashWithSalt.length);
            System.arraycopy(inputPassword.getBytes(), 0, inputPasswordWithSalt, hashWithSalt.length, inputPassword.getBytes().length);

            // 입력된 비밀번호를 해시하여 저장된 해시와 비교
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] inputPasswordBytes = md.digest(inputPasswordWithSalt);

            for (int i = 0; i < inputPasswordBytes.length; i++) {
                if (inputPasswordBytes[i] != hashWithSalt[i]) {
                    return false; // 해시가 일치하지 않음
                }
            }

            return true; // 해시가 일치함
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 오류 발생", e);
        }
    }
}
