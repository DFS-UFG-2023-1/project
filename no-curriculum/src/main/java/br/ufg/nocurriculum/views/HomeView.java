package br.ufg.nocurriculum.views;

import br.ufg.nocurriculum.entities.Profession;
import br.ufg.nocurriculum.entities.ProfessionSegmentCount;
import br.ufg.nocurriculum.entities.UserProfile;
import br.ufg.nocurriculum.services.ProfessionSegmentService;
import br.ufg.nocurriculum.services.UserProfileService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    public HomeView(
        UserProfileService userProfileService,
        ProfessionSegmentService professionSegmentService
    ) {
        this.userProfileService = userProfileService;
        this.professionSegmentService = professionSegmentService;

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
            .widthFull()
            .maxWidth("1400px")
            .margin("3em auto")
            .build();

        var talents = createTalentsComponent();

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
            addComponentAtIndex(ComponentIndex.TALENTS.index, createTalentsComponent(filter));
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
        List<ProfessionSegmentCount> allGroupedByName = professionSegmentService.getAllGroupedByName();
        return allGroupedByName.stream().map(
            it -> creatProfessionSegmentComponent(it.getName(), it.getCount())
        ).toArray(FlexLayout[]::new);
    }

    private static FlexLayout creatProfessionSegmentComponent(String name, Long count) {
        return $(new FlexLayout())
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
            .build();
    }

    private FlexLayout createTalentsComponent() {
        return $(new FlexLayout())
            .add(
                userProfileService.all().stream().map(this::createTalentComponent).toArray(Div[]::new)
            )
            .justifyAround()
            .flexWrap()
            .width("80%")
            .margin("auto")
            .build();
    }

    private FlexLayout createTalentsComponent( String filter) {
        List<UserProfile> userProfiles = userProfileService.filterByStringColumns(filter);
        return $(new FlexLayout())
            .add(
                userProfiles.stream().map(this::createTalentComponent).toArray(Div[]::new)
            )
            .justifyAround()
            .flexWrap()
            .width("80%")
            .margin("auto")
            .build();
    }

    private Div createTalentComponent(UserProfile profile) {
        return $(new Div())
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


