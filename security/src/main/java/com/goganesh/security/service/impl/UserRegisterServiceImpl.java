package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.service.*;
import com.goganesh.security.controller.dto.RegistrationFormRequest;
import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.JwtService;
import com.goganesh.security.service.PhoneNumberService;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Builder
@AllArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

    private final JwtService jwtService;
    private final PhoneNumberService phoneNumberService;

    private final Book2UserReadRepository book2UserReadRepository;
    private final Book2UserWriteRepository book2UserWriteRepository;
    private final UserContactWriteRepository userContactWriteRepository;
    private final UserContactReadRepository userContactReadRepository;
    private final UserWriteRepository userWriteRepository;
    private final UserReadRepository userReadRepository;

    @Override
    @Transactional
    public void blockTempUser(User tempUser, User realUser) {
        mergeTempUserToUser(tempUser, realUser);

        tempUser.setEnabled(false);
        userWriteRepository.save(tempUser);
    }

    @Override
    @Transactional
    public void deleteBlockedTempUser(User tempUser) {
        if (!tempUser.isEnabled()) {
            book2UserReadRepository.findByUser(tempUser)
                    .forEach(book2UserWriteRepository::delete);

            userWriteRepository.delete(tempUser);
        } else {
            //todo throw some exception;
        }

    }

    private void mergeTempUserToUser(User tempUser, User realUser) {
        mergeContactData(tempUser, realUser);
        //todo business data should be another place
        mergeBook2UserData(tempUser, realUser);
    }

    private void mergeBook2UserData(User tempUser, User realUser) {
        book2UserReadRepository.findByUser(tempUser)
                .forEach(book2User -> {
                    book2User.setUser(realUser);
                    book2UserWriteRepository.save(book2User);
                });
    }

    private void mergeContactData(User tempUser, User realUser) {
        userContactReadRepository.getApprovedContacts(tempUser)
                .forEach(userContact -> {
                    userContact.setUser(realUser);
                    userContactWriteRepository.save(userContact);
                });
    }

    @Override
    public Optional<User> getUserByHash(String hash) {
        return userReadRepository.findByHash(hash);
    }


    @Override
    public User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getUser();
    }

    @Transactional
    @Override
    public String registerNewUser(RegistrationFormRequest registrationForm) {
        final String email = registrationForm.getEmail();
        final String phone = phoneNumberService.formatPhoneNumber(registrationForm.getPhone());

        User tempUser = getCurrentUser();
        if (userContactReadRepository.getApprovedContact(phone, UserContact.ContactType.PHONE, tempUser).isPresent() &&
                userContactReadRepository.getApprovedContact(email, UserContact.ContactType.EMAIL, tempUser).isPresent()) {
            User realUser = new User();
            realUser.setName(registrationForm.getName());
            realUser.setHash(UUID.randomUUID().toString());
            realUser.setRegTime(LocalDateTime.now());
            realUser.setEnabled(true);
            realUser.setRole("USER");

            userWriteRepository.save(realUser);
            blockTempUser(tempUser, realUser);

            return jwtService.generateToken(realUser.getHash());
        }

        throw new RuntimeException("9090");//todo
    }

    private void saveContact(User user, UserContact.ContactType contactType, String contact) {
        UserContact userContact = UserContact.builder()
                .contactType(contactType)
                .user(user)
                .approved(true)
                .contact(contact)
                .build();

        userContactWriteRepository.save(userContact);
    }

    @Override
    public User registerTempUser(String email) {
        User user = new User();
        user.setName(email);
        user.setHash(UUID.randomUUID().toString());
        user.setRegTime(LocalDateTime.now());
        user.setEnabled(true);
        user.setRole("TEMP_USER"); //todo

        return userWriteRepository.save(user);
    }
}
