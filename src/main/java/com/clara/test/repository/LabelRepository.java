package com.clara.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Label;
import com.clara.test.entity.LabelProjection;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {

	Optional<Label> findByLabelName(String name);

	@Query(value = """
			SELECT l.LABEL_NAME, COUNT(1) FROM LABEL l
			JOIN RELEASE_LABEL rl
			ON l.id = rl.LABEL_ID
			JOIN ARTIST_RELEASE ar
			ON rl.RELEASE_ID = ar.RELEASE_ID
			WHERE ar.ARTIST_ID = :artistId
			GROUP BY LABEL_NAME
			ORDER BY 2 DESC
						""", nativeQuery = true)
	public List<LabelProjection> getFrequencyByArtist(Integer artistId);
}
