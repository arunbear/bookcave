package org.example.bookland.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("BOOKS")
public record BookEntity(
    @Id Long id,
    String title
) {
}
