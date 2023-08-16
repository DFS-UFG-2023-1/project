package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.DefaultProfession;
import br.ufg.nocurriculum.repositories.DefaultProfessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultProfessionService {

    private final DefaultProfessionRepository repository;

    public DefaultProfessionService(DefaultProfessionRepository repository) {
        this.repository = repository;
    }

    public List<DefaultProfession> getAll() {
        return repository.findAll();
    }
}
