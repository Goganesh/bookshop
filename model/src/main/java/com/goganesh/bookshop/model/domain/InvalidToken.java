package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "invalid_token")
public class InvalidToken extends BaseEntity {

    private String token;
}
