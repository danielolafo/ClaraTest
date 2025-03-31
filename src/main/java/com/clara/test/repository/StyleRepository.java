package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
}
