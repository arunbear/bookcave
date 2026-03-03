package org.example.bookland.repository;

import org.example.bookland.entity.BookEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends ListCrudRepository<BookEntity, Long> {

    @Override
    List<BookEntity> findAll();

    @Override
    Optional<BookEntity> findById(Long id);
}
