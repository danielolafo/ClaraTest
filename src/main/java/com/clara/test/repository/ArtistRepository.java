package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	
	/**
	 * 
	 * @param name
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public Optional<Artist> findByName(String name);

}
