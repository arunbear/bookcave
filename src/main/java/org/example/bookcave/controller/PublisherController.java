package org.example.bookcave.controller;

import jakarta.validation.Valid;
import org.example.bookcave.dto.PublisherRequest;
import org.example.bookcave.entity.PublisherEntity;
import org.example.bookcave.repository.PublisherRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@NullMarked
@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @PostMapping
    public ResponseEntity<PublisherEntity> createPublisher(@RequestBody @Valid PublisherRequest publisherRequest) {
        PublisherEntity entity = new PublisherEntity(null, publisherRequest.name());
        PublisherEntity savedEntity = publisherRepository.save(entity);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEntity.id())
                .toUri();

        return ResponseEntity.created(location).body(savedEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherEntity> getPublisher(@PathVariable Long id) {
        return publisherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
