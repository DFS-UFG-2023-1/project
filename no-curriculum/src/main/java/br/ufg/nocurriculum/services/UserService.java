package br.ufg.nocurriculum.services;

import br.ufg.nocurriculum.entities.Profession;
import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.entities.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final UserProfileService userProfileService;
    private final ProfessionService professionService;
    private final PasswordEncoder encoder;

    public UserService(UserDetailsManager userDetailsManager,
                       UserProfileService userProfileService,
                       ProfessionService professionService, PasswordEncoder encoder) {
        this.userDetailsManager = userDetailsManager;
        this.userProfileService = userProfileService;
        this.professionService = professionService;
        this.encoder = encoder;
    }

    public void save(Users user, UserProfile profile, List<Profession> professions) {
        var userDetails = User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .passwordEncoder(encoder::encode)
            .build();

        userDetailsManager.createUser(userDetails);
        userProfileService.save(profile);
        professionService.save(professions);
    }

    public void save(UserProfile profile, List<Profession> professions) {
        userProfileService.save(profile);
        professionService.save(professions);
    }


}
