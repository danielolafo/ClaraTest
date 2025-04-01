package com.clara.test.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clara.test.entity.Release;
import com.clara.test.entity.ReleaseProjection;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Integer> {

	@Query(value = """
			SELECT r.* FROM Release r
			JOIN Artist_Release ar
			ON r.id = ar.release_id
			JOIN Artist a
			ON a.id = ar.artist_id
			WHERE a.id = :artistId
			""", nativeQuery = true)
	public List<Release> findByArtist(Integer artistId);

	@Query(value = """
			SELECT a.NAME, COUNT(1)  FROM ARTIST_RELEASE  ar
			JOIN ARTIST a ON ar.artist_id = a.id
			JOIN RELEASE r
			ON ar.release_id = r.id
			WHERE a.ID= :artistId
			GROUP BY a.NAME
			""", nativeQuery = true)
	public List<ReleaseProjection> findReleaseCountByArtist(Integer artistId);

	@Query(
			"""
			SELECT r FROM Release r
			JOIN r.releaseArtistReleases ar
			ON r.id = ar.release.id
			WHERE ar.artist.id = :artistId
			""")
	public List<Release> getAllByArtist(@Param("artistId") Integer artistId, Pageable pageable);

}
