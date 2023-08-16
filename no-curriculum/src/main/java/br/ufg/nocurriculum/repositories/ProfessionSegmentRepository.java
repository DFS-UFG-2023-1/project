package br.ufg.nocurriculum.repositories;

import br.ufg.nocurriculum.entities.ProfessionSegment;
import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionSegmentRepository extends JpaRepository<ProfessionSegment, Long> {

    @Query(value = """
            SELECT name, COUNT(*) AS TOTAL
            FROM
            	(SELECT DISTINCT ps.name,
            			email
            		FROM profession_segment ps
            		LEFT JOIN (segment_default_profession sdp
            		        JOIN default_profession sp ON sp.id = sdp.profession_segment_id
            		    ) ON ps.id = sdp.default_profession_id
            		LEFT JOIN profession p ON (p.name = sp.name)
            		LEFT JOIN user_profile up ON (up.id = p.profile_id)) AS TABELA
            GROUP BY (name);
        """, nativeQuery = true)
    List<ProfessionSegmentCount> findAllGroupedByName();
}
