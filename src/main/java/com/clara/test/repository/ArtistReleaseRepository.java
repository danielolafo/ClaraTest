package com.clara.test.repository;

import java.util.List;
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
	
	@Query(value=
			"""
			SELECT DISTINCT  R.RELEASE_YEAR 
					FROM ARTIST_RELEASE ar
					JOIN RELEASE r
					ON ar.release_id = r.id
					WHERE ar.artist_id= :artistId
					AND r.RELEASE_YEAR IS NOT NULL
			ORDER BY 1
			""",
			nativeQuery=true)
	public List<Integer> getReleaseYears(Integer artistId);
}
