package br.ufg.nocurriculum.repositories.jdbc.mapper;

import br.ufg.nocurriculum.entities.Profession;
import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.entities.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserProfileRowMapper implements RowMapper<UserProfile> {

    @Override
    public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserProfile.builder()
            .socialName(rs.getString("social_name"))
            .user(Users.builder().username(rs.getString("email")).build())
            .professions(Arrays.stream(rs.getString("professions").split(",")).map(it -> Profession.builder().name(it).build()).toList())
            .build();
    }
}
