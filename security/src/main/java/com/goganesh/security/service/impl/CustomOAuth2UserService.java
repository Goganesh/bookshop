package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.service.UserContactWriteRepository;
import com.goganesh.bookshop.model.service.UserReadRepository;
import com.goganesh.bookshop.model.service.UserWriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserReadRepository userReadRepository;
    private final UserWriteRepository userWriteRepository;
    private final UserContactWriteRepository userContactWriteRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        Optional<User> user = userReadRepository.findByEmail(email);
        if (user.isEmpty()) {
            User newUser = User.builder()
                    .balance(0)
                    .isEnabled(true)
                    .name(oAuth2User.getAttribute("name"))
                    .regTime(LocalDateTime.now())
                    .role("USER")
                    .build();
            userWriteRepository.save(newUser);

            UserContact userContact = UserContact.builder()
                    .contactType(UserContact.ContactType.EMAIL)
                    .user(newUser)
                    .approved(true)
                    .contact(email)
                    .build();
            userContactWriteRepository.save(userContact);
        } else {
            //todo updateExistUser
        }

        return oAuth2User;
    }
}
