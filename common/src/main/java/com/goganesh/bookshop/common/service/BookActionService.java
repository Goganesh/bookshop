package com.goganesh.bookshop.common.service;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.User;

import java.util.List;

public interface BookActionService {

    void execute(String action, User user, List<Book> books);
}
