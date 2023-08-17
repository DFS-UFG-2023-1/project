package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.Profession;
import br.ufg.nocurriculum.repositories.jpa.ProfessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionService {

    private final ProfessionRepository repository;

    public ProfessionService(ProfessionRepository repository) {
        this.repository = repository;
    }

    public void save(List<Profession> professions) {
        repository.saveAll(professions);
    }

    public void deleteAll(List<Profession> professions) {
        repository.deleteAll(professions);
    }
}
