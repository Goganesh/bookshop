package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "document")
public class Document extends BaseEntity {

    @Column(name = "sort_index")
    private int sortIndex;

    private String slug;

    private String title;

    private String text;
}