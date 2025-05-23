package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

}
