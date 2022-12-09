package com.goganesh.bookshop.webui.client.mapper;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
@Getter
@Setter
public abstract class UserMapper {

    @Autowired
    protected Book2UserReadRepository book2UserReadRepository;

    @Mapping(target = "name",
            source = "user.name"
    )
    @Mapping(target = "email",
            expression = "java( user.getRole().equals(\"TEMP_USER\") ? \"\" : user.getEmail() )"
    )
    @Mapping(target = "phone",
            expression = "java( user.getRole().equals(\"TEMP_USER\") ? \"\" : user.getPhone() )"
    )
    @Mapping(target = "balance",
            source = "user.balance"
    )
    @Mapping(target = "isReg",
            expression = "java( !user.getRole().equals(\"TEMP_USER\") )"
    )
    @Mapping(target = "booksKept",
            expression = "java( book2UserReadRepository.findByUser(user).stream().filter(book2User -> book2User.getBook2UserType().getCode().equals(\"KEPT\")).count() )"
    )
    @Mapping(target = "booksCart",
            expression = "java( book2UserReadRepository.findByUser(user).stream().filter(book2User -> book2User.getBook2UserType().getCode().equals(\"CART\")).count() )"
    )
    @Mapping(target = "booksMy",
            expression = "java( book2UserReadRepository.findByUser(user).stream().filter(book2User -> book2User.getBook2UserType().getCode().equals(\"PAID\")).count() )"
    )
    public abstract UserPageDto toDto(User user);
}
