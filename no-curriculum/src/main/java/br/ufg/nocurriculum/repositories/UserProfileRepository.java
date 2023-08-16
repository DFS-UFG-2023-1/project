package br.ufg.nocurriculum.repositories;

import br.ufg.nocurriculum.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT up FROM UserProfile up WHERE up.user.username = :email")
    UserProfile findByEmail(@Param("email") String email);

    List<UserProfile> findByVisibleIsTrue();

    @Query("""
            SELECT up FROM UserProfile up
            JOIN FETCH Users u
            LEFT JOIN FETCH Profession p
            WHERE (
                p.name ILIKE %:value%
                OR up.user.username ILIKE %:value%
                OR up.socialName ILIKE %:value%
            ) AND up.visible = TRUE
        """)
    List<UserProfile> filterByStringColumns(String value);
}
