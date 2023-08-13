package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager manager;

    public UserService(UserDetailsManager manager) {
        this.manager = manager;
    }

    public void create(Users user) {
        var userDetails = User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
        manager.createUser(userDetails);
    }

}
