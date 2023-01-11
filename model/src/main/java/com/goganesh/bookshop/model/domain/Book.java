package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pub_date")
    private LocalDate pubDate;

    @Column(name = "is_bestseller")
    private boolean isBestseller;

    private String slug;

    private String title;

    private String image;

    private String description;

    private int price;

    private double discount;

    private double popularity;

    @ToString.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Book2Author> book2Authors = new ArrayList<>();
}
