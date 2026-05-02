package org.example.bookcave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookRequest(@NotBlank @Size(min = 2) String title, @NotNull @Positive long publisherId) {
}
