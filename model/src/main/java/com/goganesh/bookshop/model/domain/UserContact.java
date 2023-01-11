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
@Table(name = "user_contact")
public class UserContact extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean approved;

    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    private ContactType contactType;

    private String code;

    @Column(name = "code_time")
    private LocalDateTime codeTime;

    public enum ContactType {
        PHONE,
        EMAIL
    }

}
