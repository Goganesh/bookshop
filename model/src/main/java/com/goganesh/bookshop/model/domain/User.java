package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    private String hash;
    private int balance;
    private String name;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    private String role;

    @ToString.Exclude
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<UserContact> contacts = new ArrayList<>();

    //todo maybe remove to service layer
    public String getEmail() {
        return Optional.ofNullable(contacts.stream()
                .filter(contact -> contact.getContactType() == UserContact.ContactType.EMAIL && contact.isApproved())
                .findFirst()
                .get()
                .getContact()).orElse("some mail"); //todo
    }

    //todo maybe remove to service layer
    public String getPhone() {
        return Optional.ofNullable(contacts.stream()
                .filter(contact -> contact.getContactType() == UserContact.ContactType.PHONE && contact.isApproved())
                .findFirst()
                .get()
                .getContact()).orElse("some phone"); //todo
    }
}
