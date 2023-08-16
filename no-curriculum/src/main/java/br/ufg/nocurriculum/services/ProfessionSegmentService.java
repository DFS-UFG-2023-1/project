package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import br.ufg.nocurriculum.repositories.ProfessionSegmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfessionSegmentService {

    private final ProfessionSegmentRepository repository;

    public ProfessionSegmentService(ProfessionSegmentRepository repository) {
        this.repository = repository;
    }

    public List<ProfessionSegmentCount> getAllGroupedByName() {
        return repository.findAllGroupedByName();
    }
}
