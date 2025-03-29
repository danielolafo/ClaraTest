package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Release;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Integer> {

}
