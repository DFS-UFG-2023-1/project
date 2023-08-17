package br.ufg.nocurriculum.repositories.jpa;

import br.ufg.nocurriculum.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByUser_Username(String username);

    List<UserProfile> findByVisibleIsTrue();

}
