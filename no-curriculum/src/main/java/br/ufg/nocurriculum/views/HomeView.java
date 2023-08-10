package br.ufg.nocurriculum.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.io.Serial;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.*;

@Route("")
@RouteAlias("home")
@UIScope
@SpringComponent
public class HomeView extends StickedHeaderView {

    @Serial
    private static final long serialVersionUID = -3501106479253486877L;

    public HomeView() {
        var welcome = $(new FlexLayout())
            .add(
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
            .margin("auto")
            .widthFull()
            .justifyBetween()
            .columnDirection()
            .build();

        var sign = $(new FlexLayout())
            .add(
                $(new FlexLayout())
                    .add(
                        $(new EmailField())
                            .label("E-mail")
                            .autocompleteOff()
                            .build(),
                        $(new PasswordField())
                            .label("Senha")
                            .autocompleteOff()
                            .build(),
                        $(new Button())
                            .text("Login")
                            .build(),
                        $(new Span("ou"))
                            .classNames("hr-lines")
                            .build(),
                        $(new Button())
                            .navigateTo("signup")
                            .text("Inscreva-se")
                            .build()
                    )
                    .backgroundColor("#FFF")
                    .borderRadius("0.50em")
                    .maxWidth("300px")
                    .padding("2em")
                    .widthFull()
                    .alignCenter()
                    .columnDirection()
                    .build(),
                $(new Button())
                    .text("Saiba mais")
                    .suffix(new Icon(VaadinIcon.CHEVRON_CIRCLE_RIGHT))
                    .tertiary()
                    .build()
            )
            .alignCenter()
            .columnDirection()
            .margin("auto")
            .widthFull()
            .build();

        setContent(
            $(new HorizontalLayout())
                .sizeFull()
                .add(welcome, sign)
                .build()
        );

    }
}


