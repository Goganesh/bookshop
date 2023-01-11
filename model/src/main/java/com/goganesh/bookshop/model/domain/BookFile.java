package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "book_file")
@Entity
public class BookFile extends BaseEntity {

    private String hash;

    private String path;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private BookFileType type;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

}
