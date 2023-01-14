package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.webapi.client.dto.UserApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.UserApiResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchUserException;
import com.goganesh.bookshop.webapi.client.mapper.UserApiMapper;
import com.goganesh.bookshop.webapi.client.service.UserRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRestService userRestService;
    private final UserApiMapper userApiMapper;

    @GetMapping()
    public Page<UserApiResponseDto> getUsers(@PageableDefault(value = 20) Pageable pageable) {
        return userRestService.findAll(pageable).map(userApiMapper::toDto);
    }

    @GetMapping("/{id}")
    public UserApiResponseDto getUser(@PathVariable("id") Integer id) {
        User user = userRestService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        return userApiMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        User user = userRestService.findById(id).orElseThrow(() -> new NoSuchUserException("No such user with id " + id));
        userRestService.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public UserApiResponseDto postUser(@Validated @RequestBody UserApiRequestDto userApiRequestDto) {
        User existedUser = null;

        if (userApiRequestDto.getId() == -1 || Objects.isNull(userApiRequestDto.getId())) {
            userApiRequestDto.setId(null);
        } else {
            existedUser = userRestService.findById(userApiRequestDto.getId())
                    .orElseThrow(() -> new NoSuchUserException("No such user with id " + userApiRequestDto.getId()));
        }

        User user = userApiMapper.toModel(userApiRequestDto);
        if (Objects.nonNull(existedUser)) {
            user.setRegTime(existedUser.getRegTime());
        }

        user = userRestService.save(user);

        return userApiMapper.toDto(user);
    }
}
