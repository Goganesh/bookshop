package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.UserContact;

public interface UserContactWriteRepository {

    UserContact save(UserContact userContact);

}
