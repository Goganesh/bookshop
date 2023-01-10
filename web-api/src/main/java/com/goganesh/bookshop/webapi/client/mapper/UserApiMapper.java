package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webapi.client.dto.UserApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.UserApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserApiMapper {

    @Mapping(target="id",
            source = "user.id")
    @Mapping(target="regTime",
            source = "user.regTime")
    @Mapping(target="hash",
            source = "user.hash")
    @Mapping(target="balance",
            source = "user.balance")
    @Mapping(target="name",
            source = "user.name")
    @Mapping(target="enabled",
            source = "user.enabled")
    @Mapping(target="role",
            source = "user.role")
    UserApiResponseDto toDto(User user);

    @Mapping(target="id",
            source = "userApiRequestDto.id")
    @Mapping(target="name",
            source = "userApiRequestDto.name")
    @Mapping(target="hash",
            source = "userApiRequestDto.hash")
    @Mapping(target="balance",
            source = "userApiRequestDto.balance")
    @Mapping(target="role",
            source = "userApiRequestDto.role")
    User toModel(UserApiRequestDto userApiRequestDto);

}
