package com.goganesh.security.service;

import com.goganesh.bookshop.model.domain.LoginAttempt;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.domain.UserContact;

public interface LoginAttemptService {

    /**
     * Increment login fail counter by contact. If counter is full, temporary blocks user login by contact
     *
     * @param userContact - user attempt contact
     */
    void loginFailed(User user, LoginAttempt.LoginType loginType);

    boolean isBlocked(UserContact userContact);
}
