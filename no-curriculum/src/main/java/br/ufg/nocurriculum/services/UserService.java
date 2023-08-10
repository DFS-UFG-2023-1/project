package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.Users;
import br.ufg.nocurriculum.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(Users user) {
        userRepository.save(user);
    }

}
