package org.example.bookcave.controller;

import jakarta.validation.Valid;
import org.example.bookcave.dto.BookRequest;
import org.example.bookcave.entity.BookEntity;
import org.example.bookcave.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@RequestBody @Valid BookRequest bookRequest) {
        BookEntity savedEntity = bookRepository.save(new BookEntity(null, bookRequest.title()));

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEntity.id())
                .toUri();

        return ResponseEntity.created(location).body(savedEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
