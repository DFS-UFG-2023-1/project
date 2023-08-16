package br.ufg.nocurriculum.repositories;

import br.ufg.nocurriculum.entities.DefaultProfession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultProfessionRepository extends JpaRepository<DefaultProfession, Long> {
}
