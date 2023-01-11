package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "file_download")
public class FileDownload extends BaseEntity {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "book_id")
    private int bookId;

    private int count;

}
