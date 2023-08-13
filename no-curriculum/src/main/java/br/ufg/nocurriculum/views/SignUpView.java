package br.ufg.nocurriculum.views;

import br.ufg.nocurriculum.entities.Roles;
import br.ufg.nocurriculum.entities.Users;
import br.ufg.nocurriculum.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.$;

@AnonymousAllowed
@Route("signup")
@UIScope
@SpringComponent
public class SignUpView extends StickedHeaderView {

    private final static Logger LOGGER = LoggerFactory.getLogger(SignUpView.class);
    @Serial
    private static final long serialVersionUID = 7277297461948943231L;

    private final UserService userService;

    private final Dialog success = $(new Dialog())
        .add(
            new Paragraph("Cadastro realizado com sucesso!")
        )
        .closeActionOK("home")
        .build();

    private final Dialog error = $(new Dialog())
        .add(
            new Paragraph("Não foi possível realizar seu cadastro nesse momento, tente novamente mais tarde!")
        )
        .closeActionOK()
        .build();

    private final RadioButtonGroup<Roles> roles = new RadioButtonGroup<>();
    private final EmailField email = new EmailField();
    private final PasswordField password = new PasswordField();

    public SignUpView(UserService userService) {
        this.userService = userService;
        this.roles.setItems(Roles.values());
        this.roles.setValue(Roles.USER);
        this.roles.setItemLabelGenerator(Roles::getDescription);

        var signUp = $(new FlexLayout())
            .add(
                $(new H1())
                    .text("Cadastro")
                    .alignCenter()
                    .build(),
                roles,
                $(email)
                    .placeholder("E-mail")
                    .build(),
                $(password)
                    .placeholder("Senha")
                    .build(),
                $(new Button())
                    .text("Cadastre-se")
                    .clickListener(e -> saveUser())
                    .build(),
                $(new Button())
                    .text("Cancelar")
                    .tertiary()
                    .navigateTo("home")
                    .build()
            )
            .columnDirection()
            .margin("auto")
            .build();

        setContent(
            $(new VerticalLayout(signUp))
                .sizeFull()
                .build()
        );
    }

    private void saveUser() {
        try {
            userService.create(new Users(email.getValue(), password.getValue(), roles.getValue().name()));
            success.open();
        } catch (Exception err) {
            LOGGER.error("fail to save user", err);
            error.open();
        }
    }
}
