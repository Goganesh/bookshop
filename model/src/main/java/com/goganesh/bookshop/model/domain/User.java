package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    private String hash;
    private int balance;
    private String name;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    private String role;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserContact> contacts = new ArrayList<>();

    @Builder
    public User(Integer id, LocalDateTime regTime, String hash, int balance, String name, boolean isEnabled, String role, List<UserContact> contacts) {
        super.setId(id);
        this.regTime = regTime;
        this.hash = hash;
        this.balance = balance;
        this.name = name;
        this.isEnabled = isEnabled;
        this.role = role;
        this.contacts = contacts;
    }
}
