package org.example.bookcave.repository;

import org.example.bookcave.entity.PublisherEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends ListCrudRepository<PublisherEntity, Long> {

    @Override
    List<PublisherEntity> findAll();

    @Override
    Optional<PublisherEntity> findById(Long id);
}
