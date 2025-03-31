package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	Optional<Genre> findByGenreName(String name);
}
