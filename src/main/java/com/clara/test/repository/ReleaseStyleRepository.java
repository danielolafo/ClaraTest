package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.ReleaseStyle;

@Repository
public interface ReleaseStyleRepository extends JpaRepository<ReleaseStyle, Integer> {
}
