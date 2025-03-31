package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.ReleaseGenre;

@Repository
public interface ReleaseGenreRepository extends JpaRepository<ReleaseGenre, Integer> {
	
	@Query(value="""
			SELECT rg.* FROM Release_Genre rg
			WHERE rg.release_id = :releaseId
			AND rg.genre_id = :genreId
			""", nativeQuery=true)
	public Optional<ReleaseGenre> findByGenreAndRelease(Integer releaseId, Integer genreId);
}
