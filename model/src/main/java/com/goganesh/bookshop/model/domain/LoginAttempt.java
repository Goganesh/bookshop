package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "login_attempt")
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "login_type")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(name = "attempt_count")
    private int attemptCount;

    @Column(name = "attempt_time")
    private LocalDateTime attemptTime;

    public enum LoginType {
        EMAIL,
        PHONE,
    }
}
