package br.ufg.nocurriculum.views;

import br.ufg.nocurriculum.entities.Profession;
import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.events.FlexLayoutDeselectEvent;
import br.ufg.nocurriculum.services.ProfessionSegmentService;
import br.ufg.nocurriculum.services.UserProfileService;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.Serial;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.$;
import static java.util.stream.Collectors.joining;

@AnonymousAllowed
@Route(value = "", layout = MainLayout.class)
@PageTitle("Home | No-curriculum")
public class HomeView extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = -3501106479253486877L;

    private final UserProfileService userProfileService;
    private final ProfessionSegmentService professionSegmentService;
    private final FlexLayout talents;

    public HomeView(
        UserProfileService userProfileService,
        ProfessionSegmentService professionSegmentService
    ) {
        this.userProfileService = userProfileService;
        this.professionSegmentService = professionSegmentService;
        List<UserProfile> all = userProfileService.all();
        this.talents = createTalentsComponent(all);

        var search = $(new TextField())
            .placeholder("Pesquisar")
            .clearButtonEnable()
            .prefix(VaadinIcon.SEARCH.create())
            .maxWidth("300px")
            .widthFull()
            .build();

        var professionSegments = $(new FlexLayout())
            .add(createProfessionSegmentComponents())
            .justifyAround()
            .flexWrap()
            .maxWidth("1400px")
            .margin("3em auto")
            .width("80%")
            .build();


        var filtered = new AtomicBoolean(false);
        search.addKeyPressListener(Key.ENTER, e -> {
            var filter = search.getValue();
            if (filter.isBlank() && filtered.get()) {
                filtered.set(false);
                remove(getComponentAt(ComponentIndex.TALENTS.index));
                addComponentAtIndex(ComponentIndex.TALENTS.index, talents);
                return;
            }
            filtered.set(true);
            remove(getComponentAt(ComponentIndex.TALENTS.index));
            addComponentAtIndex(ComponentIndex.TALENTS.index, createTalentsComponent(userProfileService.getTalentsByTerm(filter)));
        });

        search.addValueChangeListener(e -> {
            if (e.getValue().isBlank()) {
                filtered.set(false);
                remove(getComponentAt(ComponentIndex.TALENTS.index));
                addComponentAtIndex(ComponentIndex.TALENTS.index, talents);
            }
        });

        addComponentAtIndex(
            ComponentIndex.SEARCH.index,
            $(new Div())
                .add(search)
                .widthFull()
                .alignCenter()
                .build()
        );
        addComponentAtIndex(ComponentIndex.SEGMENTS.index, professionSegments);
        addComponentAtIndex(ComponentIndex.TALENTS.index, talents);
    }

    private FlexLayout[] createProfessionSegmentComponents() {
        List<ProfessionSegmentCount> allGroupedByName = professionSegmentService.countingTalentsBySegment();
        var segments = allGroupedByName.stream().map(
            it -> creatProfessionSegmentComponent(it.getName(), it.getTalentsCount())
        ).toArray(FlexLayout[]::new);

        for (int i = 0; i < segments.length; i++) {
            for (int j = 0; j < segments.length; j++) {
                if (i == j) {
                    continue;
                }
                var y = i;
                var x = j;
                segments[i].addClickListener(e -> ComponentUtil.fireEvent(segments[x], new FlexLayoutDeselectEvent(segments[y])));
            }
        }

        return segments;
    }

    private FlexLayout creatProfessionSegmentComponent(String name, Long count) {
        var segment = $(new FlexLayout())
            .add(
                $(new H3(name))
                    .build(),
                $(new Paragraph(count + " Talentos"))
                    .margin("0")
                    .build()
            )
            .columnDirection()
            .justifyBetween()
            .classNames("category-card-title")
            .margin("1em")
            .build();

        var selected = new AtomicBoolean(false);
        ComponentUtil.addListener(segment, FlexLayoutDeselectEvent.class, event -> selected.set(false));

        segment.addClickListener(event -> {
            selected.set(!selected.get());
            if (selected.get()) {
                remove(getComponentAt(ComponentIndex.TALENTS.index));
                addComponentAtIndex(ComponentIndex.TALENTS.index, createTalentsComponent(userProfileService.getTalentsBySegmentName(name)));
                return;
            }
            remove(getComponentAt(ComponentIndex.TALENTS.index));
            addComponentAtIndex(ComponentIndex.TALENTS.index, talents);
        });

        return segment;
    }

    private FlexLayout createTalentsComponent(List<UserProfile> profiles) {
        return $(new FlexLayout())
            .add(
                profiles.stream().map(this::createTalentComponent).toArray(Div[]::new)
            )
            .justifyAround()
            .flexWrap()
            .width("80%")
            .margin("auto")
            .build();
    }

    private Div createTalentComponent(UserProfile profile) {
        var talent = $(new Div())
            .add(
                $(new Image(new StreamResource("avatar.png",
                    () -> getClass().getResourceAsStream("/public/avatar.png")), "Avatar"))
                    .widthFull()
                    .build(),
                $(new Div())
                    .add(
                        new H4(profile.getSocialName()),
                        new Paragraph(profile.getProfessions().stream().map(Profession::getName).collect(joining(", ")))
                    )
                    .classNames("container")
                    .build()
            )
            .classNames("card")
            .build();

        Dialog dialog = createTalentInformation(profile);
        talent.addClickListener(e -> {
            dialog.open();
        });
        return talent;
    }

    private Dialog createTalentInformation(UserProfile profile) {
        var dialog = new Dialog();

        dialog.getElement().setAttribute("aria-label", "Add note");

        var dialogLayout = createDialogLayout(profile);
        dialog.add(dialogLayout);
        dialog.setHeaderTitle("Detalhes");

        var closeButton = new Button(new Icon("lumo", "cross"),
            (e) -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(closeButton);

        return dialog;
    }

    private VerticalLayout createDialogLayout(UserProfile profile) {
        var nameField = new TextField("Nome");
        nameField.setValue(profile.getSocialName());
        nameField.setReadOnly(true);
        nameField.getStyle().set("padding-top", "0");

        var emailField = new EmailField("E-mail");
        emailField.setValue(profile.getUser().getUsername());
        emailField.setReadOnly(true);

        var descriptionField = new TextArea();
        descriptionField.setValue(profile.getDescription());
        descriptionField.setReadOnly(true);

        var fieldLayout = new VerticalLayout(nameField, emailField, descriptionField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        fieldLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return fieldLayout;
    }

    private enum ComponentIndex {
        SEARCH(0),
        SEGMENTS(1),
        TALENTS(2);

        private final int index;

        ComponentIndex(int index) {
            this.index = index;
        }

    }
}


