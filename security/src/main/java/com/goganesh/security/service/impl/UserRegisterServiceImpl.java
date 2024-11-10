package com.goganesh.security.service.impl;

import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.repository.Book2UserRepository;
import com.goganesh.bookshop.model.repository.UserContactRepository;
import com.goganesh.bookshop.model.repository.UserRepository;
import com.goganesh.security.controller.dto.RegistrationFormRequest;
import com.goganesh.security.model.UserDetailsImpl;
import com.goganesh.security.service.JwtService;
import com.goganesh.security.service.PhoneNumberService;
import com.goganesh.security.service.UserRegisterService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final JwtService jwtService;
    private final PhoneNumberService phoneNumberService;

    private final Book2UserRepository book2UserRepository;
    private final UserContactRepository userContactRepository;
    private final UserRepository userRepository;

    public UserRegisterServiceImpl(JwtService jwtService,
                                   PhoneNumberService phoneNumberService,
                                   Book2UserRepository book2UserRepository,
                                   UserContactRepository userContactRepository,
                                   UserRepository userRepository) {
        this.jwtService = jwtService;
        this.phoneNumberService = phoneNumberService;
        this.book2UserRepository = book2UserRepository;
        this.userContactRepository = userContactRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void blockTempUser(User tempUser, User realUser) {
        mergeTempUserToUser(tempUser, realUser);

        tempUser.setEnabled(false);
        userRepository.save(tempUser);
    }

    @Override
    @Transactional
    public void deleteBlockedTempUser(User tempUser) {
        if (!tempUser.isEnabled()) {
            book2UserRepository.findByUser(tempUser)
                    .forEach(book2UserRepository::delete);

            userRepository.delete(tempUser);
        }
    }

    private void mergeTempUserToUser(User tempUser, User realUser) {
        mergeContactData(tempUser, realUser);
        mergeBook2UserData(tempUser, realUser);
    }

    private void mergeBook2UserData(User tempUser, User realUser) {
        book2UserRepository.findByUser(tempUser)
                .forEach(book2User -> {
                    book2User.setUser(realUser);
                    book2UserRepository.save(book2User);
                });
    }

    private void mergeContactData(User tempUser, User realUser) {
        userContactRepository.findByUserAndApproved(tempUser, true)
                .forEach(userContact -> {
                    userContact.setUser(realUser);
                    userContactRepository.save(userContact);
                });
    }

    @Override
    public Optional<User> getUserByHash(String hash) {
        return userRepository.findByHash(hash);
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
        if (userContactRepository.findByContactAndContactTypeAndApprovedAndUser(phone, UserContact.ContactType.PHONE, true, tempUser).isPresent() &&
                userContactRepository.findByContactAndContactTypeAndApprovedAndUser(email, UserContact.ContactType.EMAIL, true, tempUser).isPresent()) {
            User realUser = new User();
            realUser.setName(registrationForm.getName());
            realUser.setHash(UUID.randomUUID().toString());
            realUser.setRegTime(LocalDateTime.now());
            realUser.setEnabled(true);
            realUser.setRole("USER");

            userRepository.save(realUser);
            blockTempUser(tempUser, realUser);

            return jwtService.generateToken(realUser.getHash());
        }
        throw new IllegalArgumentException("No approved contact data in register form " + registrationForm);
    }

    @Override
    public User registerTempUser(String email) {
        User user = new User();
        user.setName(email);
        user.setHash(UUID.randomUUID().toString());
        user.setRegTime(LocalDateTime.now());
        user.setEnabled(true);
        user.setRole("TEMP_USER");

        return userRepository.save(user);
    }
}
