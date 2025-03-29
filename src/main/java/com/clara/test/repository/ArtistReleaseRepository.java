package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.ArtistRelease;

@Repository
public interface ArtistReleaseRepository extends JpaRepository<ArtistRelease, Integer> {

	@Query(value="""
			SELECT ar.* FROM ARTIST_RELEASE ar 
			JOIN ARTIST a ON ar.ARTIST_ID=a.ID
			JOIN RELEASE r ON ar.RELEASE_ID = r.ID
			WHERE a.name = :name
			AND r.title = :title
			""", nativeQuery=true)
	public Optional<ArtistRelease> findByArtistNameAndReleaseTitle(String name, String title);
}
