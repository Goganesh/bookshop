package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "login_attempt")
public class LoginAttempt extends BaseEntity {

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
