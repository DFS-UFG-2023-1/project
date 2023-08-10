package br.ufg.nocurriculum;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serial;

@Theme("default")
@SpringBootApplication
public class NoCurriculumApplication implements AppShellConfigurator {

	@Serial
	private static final long serialVersionUID = 1205678599796149593L;

	public static void main(String[] args) {
		SpringApplication.run(NoCurriculumApplication.class, args);
	}

}
