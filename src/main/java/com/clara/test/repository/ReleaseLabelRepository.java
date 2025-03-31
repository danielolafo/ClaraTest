package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.ReleaseLabel;

@Repository
public interface ReleaseLabelRepository extends JpaRepository<ReleaseLabel, Integer> {
	
	@Query(value="""
			SELECT rl.* FROM RELEASE_LABEL rl
			WHERE rl.release_id = :releaseId
			AND rl.label_id = :labelId
			""", nativeQuery=true)
	Optional<ReleaseLabel> findByReleaseAndLabel(Integer releaseId, Integer labelId);
}

