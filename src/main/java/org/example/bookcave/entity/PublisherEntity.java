package org.example.bookcave.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("PUBLISHER")
public record PublisherEntity(
    @Id Long id,
    String name
) {
}
