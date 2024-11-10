package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book2user")
public class Book2User extends BaseEntity {

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Book2UserType book2UserType;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_enabled")
    private boolean isEnabled;
}