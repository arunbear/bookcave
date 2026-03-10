package org.example.bookcave.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("BOOK")
public record BookEntity(
    @Id Long id,
    String title
) {
}
