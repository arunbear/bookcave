package org.example.bookcave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublisherRequest(@NotBlank @Size(min = 2) String name) {
}
