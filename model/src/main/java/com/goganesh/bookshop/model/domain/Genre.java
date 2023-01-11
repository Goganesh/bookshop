package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //todo replace this to service
    @OneToMany
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private List<Genre> childGenres;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Genre parent;

    private String slug;

    private String name;
}
