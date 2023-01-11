package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "author")
@Entity
public class Author extends BaseEntity {

    private String photo;

    private String slug;

    private String name;

    private String description;

    @Builder
    public Author(Integer id, String photo, String slug, String name, String description) {
        super.setId(id);
        this.photo = photo;
        this.slug = slug;
        this.name = name;
        this.description = description;
    }
}
