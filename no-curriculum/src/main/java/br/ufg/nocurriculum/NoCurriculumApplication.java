package br.ufg.nocurriculum;

import br.ufg.nocurriculum.entities.*;
import br.ufg.nocurriculum.repositories.jpa.DefaultProfessionRepository;
import br.ufg.nocurriculum.repositories.jdbc.ProfessionSegmentJdbcRepository;
import br.ufg.nocurriculum.services.UserService;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serial;
import java.util.List;

@Theme("default")
@SpringBootApplication
public class NoCurriculumApplication implements AppShellConfigurator, CommandLineRunner {

    @Serial
    private static final long serialVersionUID = 1205678599796149593L;

    private final UserService userService;
    private final DefaultProfessionRepository defaultProfessionRepository;

    public NoCurriculumApplication(UserService userService, DefaultProfessionRepository defaultProfessionRepository) {
        this.userService = userService;
        this.defaultProfessionRepository = defaultProfessionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(NoCurriculumApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var admin = Users.builder()
            .username("admin@admin.com")
            .password("admin")
            .role("ADMIN")
            .build();

        var adminProfile = UserProfile.builder()
            .socialName("Admin")
            .visible(false)
            .user(admin)
            .build();

        userService.save(admin, adminProfile, List.of());

        var mateus = Users.builder()
            .username("mateus.voltolim@gmail.com")
            .password("mateus")
            .role("USER")
            .build();
        var mateusProfile = UserProfile.builder()
            .socialName("Mateus Neves")
            .visible(true)
            .user(mateus)
            .build();

        userService.save(mateus, mateusProfile, List.of(Profession.builder().name("Software Engineer").profile(mateusProfile).build()));

        var joao = Users.builder()
            .username("joao.silva@gmail.com")
            .password("joao")
            .role("USER")
            .build();
        var joaoProfile = UserProfile.builder()
            .socialName("João Silva")
            .visible(true)
            .user(joao)
            .build();

        userService.save(joao, joaoProfile, List.of(Profession.builder().name("DevOps Engineer").profile(joaoProfile).build()));

        var maria = Users.builder()
            .username("maria.eduarda@gmail.com")
            .password("maria")
            .role("USER")
            .build();
        var mariaProfile = UserProfile.builder()
            .socialName("Maria Eduarda")
            .visible(true)
            .user(maria)
            .build();

        userService.save(maria, mariaProfile, List.of(Profession.builder().name("DevOps Engineer").profile(mariaProfile).build(), Profession.builder().name("Security Engineer").profile(mariaProfile).build()));

        var user2 = Users.builder()
            .username("ana.rodrigues@gmail.com")
            .password("ana")
            .role("USER")
            .build();
        var profile2 = UserProfile.builder()
            .socialName("Ana Rodrigues")
            .visible(true)
            .user(user2)
            .build();
        userService.save(user2, profile2, List.of(Profession.builder().name("Data Engineer").profile(profile2).build()));

        var user3 = Users.builder()
            .username("carlos.almeida@gmail.com")
            .password("carlos")
            .role("USER")
            .build();
        var profile3 = UserProfile.builder()
            .socialName("Carlos Almeida")
            .visible(true)
            .user(user3)
            .build();
        userService.save(user3, profile3, List.of(Profession.builder().name("Product Manager").profile(profile3).build()));

        var user4 = Users.builder()
            .username("lucia.martins@gmail.com")
            .password("lucia")
            .role("USER")
            .build();
        var profile4 = UserProfile.builder()
            .socialName("Lúcia Martins")
            .visible(true)
            .user(user4)
            .build();
        userService.save(user4, profile4, List.of(Profession.builder().name("Security Engineer").profile(profile4).build()));

        var user5 = Users.builder()
            .username("pedro.nunes@gmail.com")
            .password("pedro")
            .role("USER")
            .build();
        var profile5 = UserProfile.builder()
            .socialName("Pedro Nunes")
            .visible(true)
            .user(user5)
            .build();
        userService.save(user5, profile5, List.of(Profession.builder().name("DevOps Engineer").profile(profile5).build()));

        ProfessionSegment softwareDevelopment = ProfessionSegment.builder().name("Software Development").build();
        ProfessionSegment sre = ProfessionSegment.builder().name("SRE").build();
        ProfessionSegment dataArchitecture = ProfessionSegment.builder().name("Data Architecture").build();
        ProfessionSegment cloud = ProfessionSegment.builder().name("Cloud").build();
//        professionSegmentRepository.saveAll(List.of(
//            softwareDevelopment,
//            sre,
//            dataArchitecture,
//            cloud
//        ));

        defaultProfessionRepository.saveAll(List.of(
            DefaultProfession.builder().name("Software Engineer").professionSegment(List.of(softwareDevelopment, cloud)).build(),
            DefaultProfession.builder().name("DevOps Engineer").professionSegment(List.of(sre)).build(),
            DefaultProfession.builder().name("Data Engineer").professionSegment(List.of(dataArchitecture)).build(),
            DefaultProfession.builder().name("Security Engineer").professionSegment(List.of(sre, cloud)).build(),
            DefaultProfession.builder().name("Product Manager").professionSegment(List.of(softwareDevelopment, sre, dataArchitecture, cloud)).build()
        ));

    }
}
