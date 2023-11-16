package com.szs.domain.user.service;

import com.szs.domain.user.entity.SzsUserAllowed;
import com.szs.domain.user.helper.ShaEnc;
import com.szs.domain.user.repository.repository.UserAllowedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class UserAllowedServiceImpl implements UserAllowedService {

    private final UserAllowedRepository szsUserAllowedRepository;
    @Autowired
    public UserAllowedServiceImpl(UserAllowedRepository szsUserAllowedRepository) {
        this.szsUserAllowedRepository = szsUserAllowedRepository;
    }

    /**
     * h2는 리셋되기에 허락된 유저를 db에 매번 등록한다.
     */
    @PostConstruct
    void construct(){
        SzsUserAllowed user1 = new SzsUserAllowed();
        user1.setName("홍길동");
        user1.setRegNo(ShaEnc.hashPassword("860824-1655068"));

        SzsUserAllowed user2 = new SzsUserAllowed();
        user2.setName("김둘리");
        user2.setRegNo(ShaEnc.hashPassword("921108-1582816"));

        SzsUserAllowed user3 = new SzsUserAllowed();
        user3.setName("마징가");
        user3.setRegNo(ShaEnc.hashPassword("880601-2455116"));

        SzsUserAllowed user4 = new SzsUserAllowed();
        user4.setName("베지터");
        user4.setRegNo(ShaEnc.hashPassword("910411-1656116"));

        SzsUserAllowed user5 = new SzsUserAllowed();
        user5.setName("손오공");
        user5.setRegNo(ShaEnc.hashPassword("820326-2715702"));

        szsUserAllowedRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5));
    }

    @Override
    public boolean isAllowed(String name, String regNo) {
        List<SzsUserAllowed> users = szsUserAllowedRepository.findByNameAndRegNo(name, ShaEnc.hashPassword(regNo) );
        return !users.isEmpty();
    }
}
