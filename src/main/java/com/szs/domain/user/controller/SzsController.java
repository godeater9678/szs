package com.szs.domain.user.controller;

import com.szs.domain.user.dto.SzsResponse;
import com.szs.domain.user.dto.UserDto;
import com.szs.domain.user.entity.SzsUser;
import com.szs.domain.user.facade.UserFacade;
import com.szs.domain.user.helper.JwtUtil;
import com.szs.domain.user.helper.UserMapper;
import com.szs.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value="/szs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SzsController {
    private final UserFacade userFacade;
    private final UserMapper userMapper;
    @Autowired
    public SzsController(UserMapper userMapper, UserFacade userFacade) {
        this.userMapper = userMapper;
        this.userFacade = userFacade;
    }

    @PostMapping("/signup")
    public SzsResponse<UserDto> signup(@RequestBody UserDto userDto) throws Exception {
        // 입력된 파라미터로 User 엔티티 생성
        SzsUser szsUser = userMapper.convertToEntity(userDto);
        // UserService를 사용하여 사용자 정보를 저장
        userFacade.saveUser(szsUser);
        //보안상 저장된 정보를 반환하는 것은 옳지 않으나 개발테스를 위해 추가합니다.
        return new SzsResponse<>(HttpStatus.CREATED, userDto);
    }

    @PostMapping("/login")
    public SzsResponse<String> signup(@RequestParam @NotBlank String userId
            , @RequestParam @NotBlank String password) throws Exception {
        // 입력된 파라미터로 User 엔티티 생성
        String token = userFacade.login(userId, password);
        return new SzsResponse<>(HttpStatus.ACCEPTED, token, null);
    }

    @GetMapping("/me")
    //public SzsResponse<UserDto> me(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
    public SzsResponse<UserDto> me(HttpServletRequest request) throws Exception {
        String tokenString = JwtUtil.getTokenFromHeaders(request);
        UserDto userDto = userMapper.convertToDto( userFacade.getUserByToken(tokenString) );
        return new SzsResponse<>(HttpStatus.ACCEPTED, userDto, null);
    }

    @PostMapping("/scrap")
    public SzsResponse<Object> signup(HttpServletRequest request) throws Exception {
        String tokenString = JwtUtil.getTokenFromHeaders(request);
        UserDto userDto = userMapper.convertToDto( userFacade.getUserByToken(tokenString) );

        return new SzsResponse<>(HttpStatus.ACCEPTED, userFacade.getScrap(userDto).getData(), null);
    }

    @GetMapping("/refund")
    public SzsResponse<Object> refund(HttpServletRequest request) throws Exception {
        String tokenString = JwtUtil.getTokenFromHeaders(request);
        UserDto userDto = userMapper.convertToDto( userFacade.getUserByToken(tokenString) );

        return new SzsResponse<>(HttpStatus.ACCEPTED, userFacade.getRefund(userDto), null);
    }
}
