package com.goganesh.bookshop.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "activity")
@Entity
public class Activity extends BaseEntity {

    private String name;

    private LocalDate date;

    private int count;

}
