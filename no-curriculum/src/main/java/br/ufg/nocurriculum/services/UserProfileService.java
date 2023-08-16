package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public void save(UserProfile userProfile) {
        repository.save(userProfile);
    }

    public UserProfile getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<UserProfile> all() {
        return repository.findByVisibleIsTrue();
    }

    public  List<UserProfile> filterByStringColumns(String value) {
        return repository.filterByStringColumns(value);
    }
}
