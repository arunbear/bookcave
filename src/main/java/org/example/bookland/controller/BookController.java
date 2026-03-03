package org.example.bookland.controller;

import org.example.bookland.dto.BookDto;
import org.example.bookland.dto.BookRequest;
import org.example.bookland.entity.BookEntity;
import org.example.bookland.repository.BookRepository;
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
    public ResponseEntity<BookDto> createBook(@RequestBody BookRequest bookRequest) {
        BookEntity savedEntity = bookRepository.save(new BookEntity(null, bookRequest.title()));
        BookDto bookDto = new BookDto(savedEntity.id(), savedEntity.title());

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookDto.id())
                .toUri();

        return ResponseEntity.created(location).body(bookDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(entity -> new BookDto(entity.id(), entity.title()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
