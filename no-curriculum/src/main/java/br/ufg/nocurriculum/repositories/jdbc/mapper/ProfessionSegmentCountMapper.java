package br.ufg.nocurriculum.repositories.jdbc.mapper;

import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessionSegmentCountMapper implements RowMapper<ProfessionSegmentCount> {

    @Override
    public ProfessionSegmentCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProfessionSegmentCount.builder()
            .name(rs.getString("name"))
            .talentsCount(rs.getLong("talents_count"))
            .build();
    }
}
