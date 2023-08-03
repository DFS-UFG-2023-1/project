package br.ufg.nocurriculum;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Theme("default")
@SpringBootApplication
public class NoCurriculumApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(NoCurriculumApplication.class, args);
	}

}
