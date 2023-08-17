package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import br.ufg.nocurriculum.repositories.jdbc.ProfessionSegmentJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionSegmentService {

    private final ProfessionSegmentJdbcRepository repository;

    public ProfessionSegmentService(ProfessionSegmentJdbcRepository repository) {
        this.repository = repository;
    }

    public List<ProfessionSegmentCount> countingTalentsBySegment() {
        return repository.countingTalentsBySegment();
    }
}
