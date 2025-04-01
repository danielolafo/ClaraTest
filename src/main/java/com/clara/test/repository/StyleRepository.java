package com.clara.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.dto.StyleProjection;
import com.clara.test.entity.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {

	Optional<Style> findByStyleName(String styleName);

	@Query(value = """
			SELECT s.* FROM STYLE s JOIN RELEASE_STYLE rs
			ON s.id = rs.style_id
			JOIN ARTIST_RELEASE ra
			ON rs.release_id = ra.release_id
			WHERE ra.artist_id= :artistId;
			""", nativeQuery = true)
	public List<Style> findByArtistId(Integer artistId);

	@Query(value=
			"""
			SELECT s.STYLE_NAME, COUNT(1) FROM STYLE s
			JOIN RELEASE_STYLE rs
			ON s.ID = rs.STYLE_ID
			JOIN ARTIST_RELEASE ar
			ON rs.RELEASE_ID = ar.RELEASE_ID
			WHERE ar.ARTIST_ID = :artistId
			GROUP BY s.STYLE_NAME
			ORDER BY 2 DESC
		    """,
		    nativeQuery=true)
	public List<StyleProjection> getStyleFrequencyByArtist(Integer artistId);
}
