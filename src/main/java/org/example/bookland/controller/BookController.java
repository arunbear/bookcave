package org.example.bookland.controller;

import org.example.bookland.dto.BookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/book")
public class BookController {

    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<Map<String, String>> createBook(@RequestBody BookRequest bookRequest) {
        Long id = idGenerator.getAndIncrement();
        String title = bookRequest.title();

        Map<String, String> response = Map.of(
            "id", id.toString(),
            "title", title
        );

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
