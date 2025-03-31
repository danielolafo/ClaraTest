package com.clara.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	Optional<Genre> findByGenreName(String name);
	
	@Query(value=
			"""
			SELECT g.* FROM GENRE g
			JOIN RELEASE_GENRE gr
			ON g.id = gr.genre_id
			JOIN ARTIST_RELEASE ar
			ON gr.release_id = ar.release_id
			WHERE ar.artist_id= :artistId
			""",
			nativeQuery=true)
	public List<Genre> findByArtist(Integer artistId);
}
