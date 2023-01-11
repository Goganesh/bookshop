package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.exception.NoSuchBookActionException;
import com.goganesh.bookshop.common.service.BookAction;
import com.goganesh.bookshop.common.service.BookActionService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BookActionServiceImpl implements BookActionService {

    private final BookActionKept bookActionKept;
    private final BookActionUnlink bookActionUnlink;
    private final BookActionCart bookActionCart;

    @Override
    public void execute(String action, User user, List<Book> books) {
        BookAction bookAction;
        switch (action) {
            case "KEPT":
                bookAction = bookActionKept;
                break;
            case "UNLINK":
                bookAction = bookActionUnlink;
                break;
            case "CART":
                bookAction = bookActionCart;
                break;
            default:
                throw new NoSuchBookActionException("There is no action - " + action);
        }

        bookAction.execute(user, books);
    }
}
