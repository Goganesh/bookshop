package com.goganesh.bookshop.webui.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPageDto {

    private String name;
    private String email;
    private String phone;
    private int balance;
    private Boolean isReg;
    private long booksKept;
    private long booksCart;
    private long booksMy;
}
