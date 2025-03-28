package com.clara.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	
	

}
