package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.UserContactRepository;
import com.goganesh.bookshop.model.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;

    public CustomOAuth2UserService(UserRepository userRepository, UserContactRepository userContactRepository) {
        this.userRepository = userRepository;
        this.userContactRepository = userContactRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            User newUser = User.builder()
                    .balance(0)
                    .isEnabled(true)
                    .name(oAuth2User.getAttribute("name"))
                    .regTime(LocalDateTime.now())
                    .role("USER")
                    .build();
            userRepository.save(newUser);

            UserContact userContact = UserContact.builder()
                    .contactType(UserContact.ContactType.EMAIL)
                    .user(newUser)
                    .approved(true)
                    .contact(email)
                    .build();
            userContactRepository.save(userContact);
        }

        return oAuth2User;
    }
}
