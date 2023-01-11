package com.goganesh.security.controller;

import com.goganesh.bookshop.model.domain.UserContact;
import com.goganesh.bookshop.model.service.UserContactReadRepository;
import com.goganesh.bookshop.model.service.UserContactWriteRepository;
import com.goganesh.otp.service.OtpService;
import com.goganesh.security.controller.dto.ApproveContactRequest;
import com.goganesh.security.controller.dto.ApproveContactResponse;
import com.goganesh.security.controller.dto.ContactConfirmationRequest;
import com.goganesh.security.controller.dto.ContactConfirmationResponse;
import com.goganesh.security.service.PhoneNumberService;
import com.goganesh.security.service.UserRegisterService;
import lombok.Builder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@Builder
public class ContactApproveController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final UserRegisterService userRegisterService;
    private final OtpService otpService;
    private final UserContactReadRepository userContactReadRepository;
    private final UserContactWriteRepository userContactWriteRepository;
    private final PhoneNumberService phoneNumberService;

    /**
     * Send confirmation otp to new contact
     *
     * @param payload - dto contains full information for send otp
     * @return - result
     */
    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @PostMapping("/request-contact-confirmation")
    public ContactConfirmationResponse requestContactConfirmation(@Valid @RequestBody ContactConfirmationRequest payload) {
        final ContactConfirmationResponse response = ContactConfirmationResponse.builder()
                .result("true")
                .build();

        final UserContact.ContactType contactType = userContactReadRepository.defineContactType(payload.getType());
        //todo check mail or phone format
        final String contact = contactType == UserContact.ContactType.PHONE ?
                phoneNumberService.formatPhoneNumber(payload.getContact()) : payload.getContact();

        userContactReadRepository.getApprovedContact(contact, contactType).ifPresentOrElse(
                userContact -> {
                    response.setResult("false");
                    logger.info(String.format("Can not send confirmation otp for approved contact - %s", userContact));
                },
                () -> {
                    UserContact userContact = UserContact.builder()
                            .contactType(contactType)
                            .contact(contact)
                            .user(userRegisterService.getCurrentUser())
                            .approved(false)
                            .build();

                    userContactWriteRepository.save(userContact);
                    otpService.sendOtp(userContact);
                });

        return response;
    }

    /**
     * Approve otp code for confirm new contact
     *
     * @param payload - dto contains full information for approve otp code
     * @return - result
     */
    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @PostMapping("/approve-contact")
    public ApproveContactResponse approveContact(@Valid @RequestBody ApproveContactRequest payload) {
        final ApproveContactResponse response = ApproveContactResponse.builder()
                .result("false")
                .build();

        final String code = payload.getCode();
        final UserContact.ContactType contactType = userContactReadRepository.defineContactType(payload.getType());
        //todo check mail or phone format
        final String contact = contactType == UserContact.ContactType.PHONE ?
                phoneNumberService.formatPhoneNumber(payload.getContact()) : payload.getContact();

        otpService.verifyCode(contact, contactType, code)
                .ifPresentOrElse(
                        userContact -> {
                            userContact.setApproved(true);
                            userContactWriteRepository.save(userContact);
                            response.setResult("true");
                        },
                        () -> logger.info(String.format("Verification failed for contact %s %s by code %s", contactType, contact, code))
                );

        return response;
    }
}
