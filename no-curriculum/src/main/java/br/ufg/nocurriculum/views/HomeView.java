package br.ufg.nocurriculum.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.*;

@Route("")
public class HomeView extends HorizontalLayout {

    public HomeView() {

        var welcome = $(new FlexLayout())
            .add(
                $(new H2())
                    .text("no-curriculum")
                    .margin("1em 2em")
                    .build(),
                $(new FlexLayout())
                    .add(
                        $(new H1())
                            .text("Olá, seja bem vindo(a)")
                            .widthFull()
                            .maxWidth("440px")
                            .build(),
                        $(new Paragraph())
                            .text("A nossa plataforma tem o que há de mais moderno para melhor atender as suas necessidades. Temos um ambiente que propicia o cadastro de oportunidades de projetos e profissionais recém formados.")
                            .widthFull()
                            .maxWidth("440px")
                            .build()
                    )
                    .widthFull()
                    .alignEnd()
                    .justifyBetween()
                    .columnDirection()
                    .build(),
                new Div()
            )
            .marginRight("2rem")
            .widthFull()
            .justifyBetween()
            .columnDirection()
            .build();

        var sign = $(new FlexLayout())
            .add(
                $(new FlexLayout())
                    .add(
                        new Paragraph("Inscreva-se"),
                        $(new FlexLayout())
                            .add(
                                $(new Button())
                                    .text("Continuar com Facebook")
                                    .widthFull()
                                    .build(),
                                $(new Button())
                                    .text("Continuar com Google")
                                    .widthFull()
                                    .build()
                            )
                            .columnDirection()
                            .alignStart()
                            .alignContentStart()
                            .build(),
                        new Hr(),
                        new Span("ou"),
                        new Button("Continue com um e-mail")
                    )
                    .backgroundColor("#FFF")
                    .borderRadius("0.50em")
                    .maxWidth("300px")
                    .padding("2em")
                    .widthFull()
                    .alignCenter()
                    .columnDirection()
                    .build(),
                new Span("Saiba mais")
            )
            .alignCenter()
            .columnDirection()
            .margin("auto")
            .widthFull()
            .build();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.AROUND);
        add(welcome, sign);
    }
}
