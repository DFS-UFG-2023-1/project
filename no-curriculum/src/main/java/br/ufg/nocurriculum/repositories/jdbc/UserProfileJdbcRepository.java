package br.ufg.nocurriculum.repositories.jdbc;

import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.repositories.jdbc.mapper.UserProfileRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static br.ufg.nocurriculum.repositories.jdbc.queries.UserProfileQuery.SELECT_TALENTS_BY_SEGMENT;
import static br.ufg.nocurriculum.repositories.jdbc.queries.UserProfileQuery.SELECT_TALENTS_BY_TERM;

@Repository
public class UserProfileJdbcRepository {


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProfileJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserProfile> getTalentsByTerm(String term) {
        return jdbcTemplate.query(SELECT_TALENTS_BY_TERM, Map.of("term", "%" + term + "%"), new UserProfileRowMapper());
    }

    public List<UserProfile> getTalentsBySegment(String name) {
        return jdbcTemplate.query(SELECT_TALENTS_BY_SEGMENT, Map.of("name", name), new UserProfileRowMapper());
    }
}
