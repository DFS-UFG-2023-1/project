package br.ufg.nocurriculum.views;

import br.ufg.nocurriculum.services.SecurityService;
import br.ufg.nocurriculum.services.UserProfileService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.Serial;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.$;

public class MainLayout extends AppLayout {

    @Serial
    private static final long serialVersionUID = -3044120351929185815L;
    private final SecurityService securityService;
    private final UserProfileService userProfileService;

    public MainLayout(SecurityService securityService, UserProfileService userProfileService) {
        this.securityService = securityService;
        this.userProfileService = userProfileService;

        var logo = createLogo();

        HorizontalLayout header;
        if (securityService.isAuthenticated()) {
            var user = securityService.getAuthenticatedUser().getUsername();
            header = new HorizontalLayout(logo, createLogoutButton(user));
        } else {
            header = new HorizontalLayout(logo, new Div(
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

        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setWidthFull();

        addToNavbar(
            header
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
            .text("Sair, " + userProfileService.getByEmail(user).getSocialName())
            .clickListener(e -> securityService.logout())
            .build();
    }
}
