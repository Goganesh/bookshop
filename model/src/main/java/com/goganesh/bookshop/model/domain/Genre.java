package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Genre parent;

    private String slug;

    private String name;

    @Builder
    public Genre(Integer id, Genre parent, String slug, String name) {
        super.setId(id);
        this.parent = parent;
        this.slug = slug;
        this.name = name;
    }
}
