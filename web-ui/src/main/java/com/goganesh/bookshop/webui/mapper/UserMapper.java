package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.Book2UserRepository;
import com.goganesh.bookshop.model.repository.UserContactRepository;
import com.goganesh.bookshop.webui.client.dto.UserPageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", imports = UserContact.class)
@Getter
@Setter
public abstract class UserMapper {

    @Autowired
    protected Book2UserRepository book2UserRepository;

    @Autowired
    protected UserContactRepository userContactRepository;

    @Mapping(target = "name",
            source = "user.name"
    )
    @Mapping(target = "email",
            expression = "java( user.getRole().equals(\"TEMP_USER\") ? \"\" : userContactRepository.findByContactTypeAndApprovedAndUser(UserContact.ContactType.EMAIL, true, user).get().getContact() )"
    )
    @Mapping(target = "phone",
            expression = "java( user.getRole().equals(\"TEMP_USER\") ? \"\" : userContactRepository.findByContactTypeAndApprovedAndUser(UserContact.ContactType.PHONE, true, user).get().getContact() )"
    )
    @Mapping(target = "balance",
            source = "user.balance"
    )
    @Mapping(target = "isReg",
            expression = "java( !user.getRole().equals(\"TEMP_USER\") )"
    )
    @Mapping(target = "booksKept",
            expression = "java( book2UserRepository.findByUser(user).stream().filter(book2User -> book2User.getBook2UserType().getCode().equals(\"KEPT\")).count() )"
    )
    @Mapping(target = "booksCart",
            expression = "java( book2UserRepository.findByUser(user).stream().filter(book2User -> book2User.getBook2UserType().getCode().equals(\"CART\")).count() )"
    )
    @Mapping(target = "booksMy",
            expression = "java( book2UserRepository.findByUser(user).stream().filter(book2User -> book2User.getBook2UserType().getCode().equals(\"PAID\")).count() )"
    )
    public abstract UserPageDto toDto(User user);
}
