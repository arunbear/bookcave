package org.example.bookland.controller;

import org.example.bookland.dto.BookDto;
import org.example.bookland.dto.BookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/book")
public class BookController {

    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Map<Long, String> books = new java.util.concurrent.ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookRequest bookRequest) {
        Long id = idGenerator.getAndIncrement();
        String title = bookRequest.title();
        books.put(id, title);

        BookDto bookDto = new BookDto(id, title);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).body(bookDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        String title = books.get(id);
        if (title == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new BookDto(id, title));
    }
}
