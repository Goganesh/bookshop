package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_review")
public class BookReview extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime time;

    private String text;

    @ToString.Exclude
    @OneToMany(mappedBy = "bookReview", fetch = FetchType.LAZY)
    private List<BookReviewLike> bookReviewLikes = new ArrayList<>();

    @Builder
    public BookReview(Integer id, Book book, User user, LocalDateTime time, String text, List<BookReviewLike> bookReviewLikes) {
        this.book = book;
        this.user = user;
        this.time = time;
        this.text = text;
        this.bookReviewLikes = bookReviewLikes;
    }
}
