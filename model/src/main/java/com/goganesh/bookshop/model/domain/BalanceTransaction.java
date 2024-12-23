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
@Table(name = "balance_transaction")
public class BalanceTransaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime time;

    private int value;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "description")
    private String description;
}
