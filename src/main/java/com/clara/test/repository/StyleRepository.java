package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
	
	Optional<Style> findByStyleName(String styleName);
}
