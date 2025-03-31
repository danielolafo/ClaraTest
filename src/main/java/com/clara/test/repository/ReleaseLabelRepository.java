package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.ReleaseLabel;

@Repository
public interface ReleaseLabelRepository extends JpaRepository<ReleaseLabel, Integer> {
}

