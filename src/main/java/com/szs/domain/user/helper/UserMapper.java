package com.szs.domain.user.helper;

import com.szs.domain.user.dto.UserDto;
import com.szs.domain.user.entity.SzsUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public SzsUser convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, SzsUser.class);
    }
    public UserDto convertToDto(SzsUser szsUser) {
        return modelMapper.map(szsUser, UserDto.class);
    }
}
