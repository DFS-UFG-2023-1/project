package br.ufg.nocurriculum.repositories.jpa;

import br.ufg.nocurriculum.entities.DefaultProfession;
import br.ufg.nocurriculum.entities.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {
}
