package br.ufg.nocurriculum.repositories.jdbc;

import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import br.ufg.nocurriculum.repositories.jdbc.mapper.ProfessionSegmentCountMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static br.ufg.nocurriculum.repositories.jdbc.queries.ProfessionSegmentCountQuery.COUNT_TALENTS_BY_SEGMENT;

@Repository
public class ProfessionSegmentJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProfessionSegmentJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProfessionSegmentCount> countingTalentsBySegment() {
       return jdbcTemplate.query(COUNT_TALENTS_BY_SEGMENT, new ProfessionSegmentCountMapper());
    }
}
