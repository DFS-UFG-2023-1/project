package br.ufg.nocurriculum.views;

import br.ufg.nocurriculum.entities.DefaultProfession;
import br.ufg.nocurriculum.entities.Profession;
import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.entities.Users;
import br.ufg.nocurriculum.services.DefaultProfessionService;
import br.ufg.nocurriculum.services.SecurityService;
import br.ufg.nocurriculum.services.UserProfileService;
import br.ufg.nocurriculum.services.UserService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.$;

public class MainLayout extends AppLayout {

    @Serial
    private static final long serialVersionUID = -3044120351929185815L;
    private final static Logger LOGGER = LoggerFactory.getLogger(MainLayout.class);

    private final SecurityService securityService;
    private final UserProfileService userProfileService;
    private final UserService userService;
    private final DefaultProfessionService defaultProfessionService;
    private final FlexLayout badges = $(new FlexLayout())
        .maxWidth("250px")
        .flexWrap()
        .build();
    private final Dialog success = $(new Dialog())
        .add(
            new Paragraph("Perfil atualizado com sucesso!")
        )
        .closeActionOK("/")
        .build();

    private final Dialog error = $(new Dialog())
        .add(
            new Paragraph("Não foi possível atualizar seu perfil nesse momento, tente novamente mais tarde!")
        )
        .closeActionOK()
        .build();

    public MainLayout(SecurityService securityService, UserProfileService userProfileService, UserService userService, DefaultProfessionService defaultProfessionService) {
        this.securityService = securityService;
        this.userProfileService = userProfileService;
        this.userService = userService;
        this.defaultProfessionService = defaultProfessionService;

        HorizontalLayout header;
        if (securityService.isAuthenticated()) {
            var user = securityService.getAuthenticatedUser().getUsername();
            header = new HorizontalLayout(createLogoutButton(user), createProfileButton(user));
        } else {
            header = new HorizontalLayout(new Div(
                $(new Button())
                    .text("Login")
                    .navigateTo("login")
                    .build(),
                $(new Button())
                    .text("Inscrever-se")
                    .navigateTo("/signup")
                    .build()
            ));
        }

        header.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        header.setWidthFull();


        HorizontalLayout horizontalLayout = new HorizontalLayout(createLogo(), header);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        horizontalLayout.setWidthFull();
        addToNavbar(
            horizontalLayout
        );


    }

    private static FlexLayout createLogo() {
        return $(new FlexLayout())
            .add(
                $(new Icon(VaadinIcon.CIRCLE))
                    .classNames("title")
                    .build(),
                $(new H2())
                    .text("no-curriculum")
                    .build()
            )
            .margin("1em 2em")
            .alignCenter()
            .justifyEvenly()
            .widthFull()
            .maxWidth("300px")
            .build();
    }

    private Button createLogoutButton(String user) {
        return $(new Button())
            .text("Sair, " + userProfileService.getByUsername(user).getSocialName())
            .clickListener(e -> securityService.logout())
            .build();
    }

    private Button createProfileButton(String user) {
        return $(new Button())
            .icon(VaadinIcon.USER)
            .clickListener(e -> {
                var profile = userProfileService.getByUsername(user);
                createProfileDialog(profile).open();
            })
            .build();
    }

    private Dialog createProfileDialog(UserProfile profile) {
        var dialog = new Dialog();

        dialog.getElement().setAttribute("aria-label", "Add note");

        var dialogLayout = createDialogLayout(dialog, profile);
        dialog.add(dialogLayout);
        dialog.setHeaderTitle("Perfil");

        var closeButton = new Button(new Icon("lumo", "cross"),
            (e) -> {
                dialog.close();
                badges.removeAll();
            });
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(closeButton);

        return dialog;
    }

    private VerticalLayout createDialogLayout(Dialog dialog, UserProfile profile) {
        var selectedProfessions = new ArrayList<>(profile.getProfessions());

        selectedProfessions.forEach(it ->
            badges.add(createFilterBadge(selectedProfessions, it))
        );

        var nameField = new TextField("Nome");
        nameField.setValue(profile.getSocialName());
        nameField.getStyle().set("padding-top", "0");

        var emailField = new EmailField("E-mail");
        emailField.setValue(profile.getUser().getUsername());
        emailField.setReadOnly(true);

        var phoneField = new TextField("Telefone");
        phoneField.setValue(profile.getPhone());

        var description = new TextArea();
        description.setValue(profile.getDescription());


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

        var fieldLayout = new VerticalLayout(nameField, emailField, phoneField, professionsComboBox, badges, description, $(new Button())
            .text("Salvar")
            .clickListener(e -> saveUser(dialog, profile, nameField.getValue(), phoneField.getValue(), description.getValue(), selectedProfessions))
            .build());
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        fieldLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return fieldLayout;
    }

    private void saveUser(Dialog dialog, UserProfile profile, String socialName, String phone, String description, List<Profession> professions) {
        try {
            profile.setSocialName(socialName);
            profile.setPhone(phone);
            profile.setProfessions(professions);
            profile.setDescription(description);
            profile.setProfessions(professions);
            professions.forEach(it -> it.setProfile(profile));

            userService.save(profile, professions);
            success.open();
        } catch (Exception err) {
            LOGGER.error("fail to save user", err);
            error.open();
        } finally {
            badges.removeAll();
            dialog.close();
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
        clearButton.addClickListener(event -> {
            badge.getElement().removeFromParent();

        });

        return badge;
    }

    private Span createFilterBadge(List<Profession> professions, Profession profession) {
        var clearButton = new Button(VaadinIcon.CLOSE_SMALL.create());
        clearButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST,
            ButtonVariant.LUMO_TERTIARY_INLINE);
        clearButton.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        // Accessible button name
        clearButton.getElement().setAttribute("aria-label", "Clear filter: " + profession.getName());
        // Tooltip
        clearButton.getElement().setAttribute("title", "Clear filter: " + profession.getName());

        var badge = new Span(new Span(profession.getName()), clearButton);
        badge.getElement().getThemeList().add("badge contrast pill");

        // Add handler for removing the badge
        clearButton.addClickListener(event -> {
            badge.getElement().removeFromParent();
            professions.remove(profession);
        });

        return badge;
    }
}
