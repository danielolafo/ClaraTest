package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
	
	Optional<Label> findByLabelName(String name);
}
