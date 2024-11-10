package com.goganesh.bookshop.model.domain;

import lombok.*;

import jakarta.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book2user_type")
public class Book2UserType extends BaseEntity {

    private String code;

    private String name;
}
