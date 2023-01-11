package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "book2genre")
public class Book2Genre extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}