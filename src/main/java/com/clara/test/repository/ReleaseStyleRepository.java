package com.clara.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.ReleaseStyle;

@Repository
public interface ReleaseStyleRepository extends JpaRepository<ReleaseStyle, Integer> {
	
	@Query(value=
			"""
			SELECT rs.* FROM RELEASE_STYLE rs
			WHERE rs.release_id = :releaseId
			AND rs.style_id = :styleId
			""",
			nativeQuery=true)
	Optional<ReleaseStyle> findByReleaseAndStyle(Integer releaseId, Integer styleId);

}
