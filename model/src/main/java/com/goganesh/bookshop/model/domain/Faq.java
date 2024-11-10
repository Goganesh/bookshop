package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "faq")
public class Faq extends BaseEntity {

    @Column(name = "sort_index")
    private int sortIndex;

    private String question;

    private String answer;
}
