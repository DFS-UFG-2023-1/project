package br.ufg.nocurriculum.views;

import br.ufg.nocurriculum.entities.*;
import br.ufg.nocurriculum.services.DefaultProfessionService;
import br.ufg.nocurriculum.services.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.$;

@AnonymousAllowed
@Route(value = "signup", layout = MainLayout.class)
@PageTitle("Cadastro | No-curriclum")
public class SignUpView extends VerticalLayout {

    private final static Logger LOGGER = LoggerFactory.getLogger(SignUpView.class);
    @Serial
    private static final long serialVersionUID = 7277297461948943231L;

    private final UserService userService;

    private final Dialog success = $(new Dialog())
        .add(
            new Paragraph("Cadastro realizado com sucesso!")
        )
        .closeActionOK("/")
        .build();

    private final Dialog error = $(new Dialog())
        .add(
            new Paragraph("Não foi possível realizar seu cadastro nesse momento, tente novamente mais tarde!")
        )
        .closeActionOK()
        .build();

    private final TextField socialName = new TextField();
    private final TextField phone = new TextField();
    private final EmailField email = new EmailField();
    private final PasswordField password = new PasswordField();
    private final RadioButtonGroup<Roles> roles = new RadioButtonGroup<>();
    private final FlexLayout badges = $(new FlexLayout())
        .maxWidth("250px")
        .flexWrap()
        .build();

    public SignUpView(UserService userService, DefaultProfessionService defaultProfessionService) {
        this.userService = userService;
        this.roles.setItems(Roles.values());
        this.roles.setValue(Roles.USER);
        this.roles.setItemLabelGenerator(Roles::getDescription);

        var selectedProfessions = new ArrayList<Profession>();

        var professions = defaultProfessionService.getAll();
        var professionsComboBox = new ComboBox<DefaultProfession>("Profissão");
        professionsComboBox.setItems(professions);
        professionsComboBox.setItemLabelGenerator(DefaultProfession::getName);

        var button = new Button(new Icon(VaadinIcon.INFO_CIRCLE));
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE,
            ButtonVariant.LUMO_ICON);
        professionsComboBox.setTooltipText("Clique Enter para adicionar uma profissão não listada");
        var tooltip = professionsComboBox.getTooltip().withManual(true);
        button.addClickListener(event -> tooltip.setOpened(!tooltip.isOpened()));
        professionsComboBox.setPrefixComponent(button);

        professionsComboBox.addValueChangeListener(e -> {
            var profession = e.getValue();
            if (profession == null) {
                return;
            }
            selectedProfessions.add(Profession.builder().name(profession.getName()).build());
            badges.add(createFilterBadge(profession.getName()));
            professionsComboBox.setItems(professions.stream().filter(it -> !it.equals(profession)).toList());
            professionsComboBox.clear();
        });

        professionsComboBox.addCustomValueSetListener(e -> {
            var professionName = e.getDetail();
            if (professionName.isBlank()) {
                return;
            }
            selectedProfessions.add(Profession.builder().name(professionName).build());
            badges.add(createFilterBadge(professionName));
            professionsComboBox.clear();
        });

        var signUp = $(new FlexLayout())
            .add(
                $(new H1())
                    .text("Cadastro")
                    .alignCenter()
                    .build(),
                $(socialName)
                    .placeholder("Nome Completo")
                    .build(),
                $(email)
                    .placeholder("E-mail")
                    .build(),
                $(phone)
                    .placeholder("Telefone")
                    .build(),
                $(password)
                    .placeholder("Senha")
                    .build(),
                roles,
                professionsComboBox,
                badges,
                $(new Button())
                    .text("Cadastre-se")
                    .clickListener(e -> saveUser(selectedProfessions))
                    .build(),
                $(new Button())
                    .text("Cancelar")
                    .tertiary()
                    .navigateTo("/")
                    .build()
            )
            .columnDirection()
            .margin("auto")
            .build();
        setSizeFull();

        add(
            signUp
        );
    }

    private void saveUser(List<Profession> professions) {
        var user = Users.builder()
            .username(email.getValue())
            .password(password.getValue())
            .role(roles.getValue().name())
            .build();

        var profile = UserProfile.builder()
            .socialName(socialName.getValue())
            .phone(phone.getValue())
            .visible(true)
            .user(user)
            .build();

        professions.forEach(it -> it.setProfile(profile));

        try {
            userService.save(user, profile, professions);
            success.open();
        } catch (Exception err) {
            LOGGER.error("fail to save user", err);
            error.open();
        }
    }

    private Span createFilterBadge(String profession) {
        var clearButton = new Button(VaadinIcon.CLOSE_SMALL.create());
        clearButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST,
            ButtonVariant.LUMO_TERTIARY_INLINE);
        clearButton.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        // Accessible button name
        clearButton.getElement().setAttribute("aria-label", "Clear filter: " + profession);
        // Tooltip
        clearButton.getElement().setAttribute("title", "Clear filter: " + profession);

        var badge = new Span(new Span(profession), clearButton);
        badge.getElement().getThemeList().add("badge contrast pill");

        // Add handler for removing the badge
        clearButton.addClickListener(event -> badge.getElement().removeFromParent());

        return badge;
    }
}
