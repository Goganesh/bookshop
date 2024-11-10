package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@Entity
public class Book extends BaseEntity {

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

    @Builder
    public Book(Integer id, LocalDate pubDate, boolean isBestseller, String slug, String title, String image, String description, int price, double discount, double popularity, List<Book2Author> book2Authors) {
        super.setId(id);
        this.pubDate = pubDate;
        this.isBestseller = isBestseller;
        this.slug = slug;
        this.title = title;
        this.image = image;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.popularity = popularity;
        this.book2Authors = book2Authors;
    }
}
