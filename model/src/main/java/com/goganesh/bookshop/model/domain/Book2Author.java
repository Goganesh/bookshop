package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book2author")
public class Book2Author extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "sort_index")
    private int sortIndex;
}