package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
}
