package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.repositories.jdbc.UserProfileJdbcRepository;
import br.ufg.nocurriculum.repositories.jpa.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository jpaRepository;
    private final UserProfileJdbcRepository jdbcRepository;

    public UserProfileService(UserProfileRepository jpaRepository, UserProfileJdbcRepository jdbcRepository) {
        this.jpaRepository = jpaRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public void save(UserProfile userProfile) {
        jpaRepository.save(userProfile);
    }

    public UserProfile getByUsername(String username) {
        return jpaRepository.findByUser_Username(username);
    }

    public List<UserProfile> all() {
        return jpaRepository.findByVisibleIsTrue();
    }

    public List<UserProfile> getTalentsByTerm(String value) {
        return jdbcRepository.getTalentsByTerm(value);
    }

    public List<UserProfile> getTalentsBySegmentName(String name) {
        return jdbcRepository.getTalentsBySegment(name);
    }
}
