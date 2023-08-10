package br.ufg.nocurriculum.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

import java.io.Serial;

import static br.ufg.nocurriculum.extensions.ComponentBuilder.$;

abstract public class StickedHeaderView extends AppLayout {

    @Serial
    private static final long serialVersionUID = -3044120351929185815L;

    public StickedHeaderView() {
        addToNavbar(
            $(new FlexLayout())
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
                .build()
        );
    }
}
