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
@Table(name = "book_review_like")
public class BookReviewLike extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "review_id")
    private BookReview bookReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime time;

    private byte value;

}