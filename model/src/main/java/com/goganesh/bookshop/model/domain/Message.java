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
@Table(name = "message")
public class Message extends BaseEntity {

    private LocalDateTime time;

    @Column(name = "user_id")
    private int userId;

    private String email;

    private String name;

    private String subject;

    private String text;
}