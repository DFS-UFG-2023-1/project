package br.ufg.nocurriculum.extensions;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.shared.HasClearButton;
import com.vaadin.flow.component.shared.HasPrefix;
import com.vaadin.flow.component.shared.HasSuffix;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.HasAutocomplete;
import com.vaadin.flow.component.textfield.TextFieldBase;

public class ComponentBuilder<T extends Component> {

    private final T component;

    private ComponentBuilder(T component) {
        this.component = component;
    }

    public static <T extends Component> ComponentBuilder<T> $(T component) {
        return new ComponentBuilder<>(component);
    }

    public ComponentBuilder<T> add(Component... components) {
        if (component instanceof HasComponents comp) {
            comp.add(components);
        }
        return this;
    }

    public ComponentBuilder<T> clickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        if (component instanceof Button comp) {
            comp.addClickListener(listener);
        }
        return this;
    }

    public ComponentBuilder<T> closeActionOK() {
        return closeAction("ok");
    }

    public ComponentBuilder<T> closeActionOK(String navigateTo) {
        return closeAction("ok", navigateTo);
    }

    public ComponentBuilder<T> closeAction(String label) {
        if (component instanceof Dialog comp) {
            var btn = new Button(label);
            comp.add(btn);

            btn.addClickListener(e -> comp.close());
        }
        return this;
    }

    public ComponentBuilder<T> closeAction(String label, String navigateTo) {
        if (component instanceof Dialog comp) {
            var btn = $(new Button())
                .text(label)
                .navigateTo(navigateTo)
                .clickListener(e -> comp.close())
                .build();

            comp.add(btn);
        }
        return this;
    }

    public ComponentBuilder<T> text(String value) {
        if (component instanceof HasText comp) {
            comp.setText(value);
        }
        return this;
    }

    public ComponentBuilder<T> label(String value) {
        if (component instanceof HasLabel comp) {
            comp.setLabel(value);
        }
        return this;
    }

    public ComponentBuilder<T> suffix(Component element) {
        if (component instanceof HasSuffix comp) {
            comp.setSuffixComponent(element);
        }
        return this;
    }

    public ComponentBuilder<T> prefix(Component element) {
        if (component instanceof HasPrefix comp) {
            comp.setPrefixComponent(element);
        }
        return this;
    }

    public ComponentBuilder<T> clearButtonEnable() {
        if (component instanceof HasClearButton comp) {
            comp.setClearButtonVisible(true);
        }
        return this;
    }

    public ComponentBuilder<T> tertiary() {
        if (component instanceof Button comp) {
            comp.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        }
        return this;
    }

    public ComponentBuilder<T> navigateTo(String route) {
        if (component instanceof Button comp) {
            comp.addClickListener(e ->
                comp.getUI().ifPresent(ui ->
                    ui.navigate(route)
                )
            );
        }
        return this;
    }

    public ComponentBuilder<T> autocompleteOff() {
        if (component instanceof HasAutocomplete comp) {
            comp.setAutocomplete(Autocomplete.OFF);
        }
        return this;
    }

    public ComponentBuilder<T> classNames(String... names) {
        component.addClassNames(names);
        return this;
    }

    public ComponentBuilder<T> margin(String value) {
        component.getStyle().set("margin", value);
        return this;
    }

    public ComponentBuilder<T> padding(String value) {
        component.getStyle().set("padding", value);
        return this;
    }

    public ComponentBuilder<T> borderRadius(String value) {
        component.getStyle().set("border-radius", value);
        return this;
    }

    public ComponentBuilder<T> backgroundColor(String value) {
        component.getStyle().set("background-color", value);
        return this;
    }

    public ComponentBuilder<T> marginRight(String value) {
        component.getStyle().set("margin-right", value);
        return this;
    }

    public ComponentBuilder<T> maxWidth(String value) {
        if (component instanceof HasSize comp) {
            comp.setMaxWidth(value);
        }
        return this;
    }

    public ComponentBuilder<T> width(String value) {
        if (component instanceof HasSize comp) {
            comp.setWidth(value);
        }
        return this;
    }

    public ComponentBuilder<T> widthFull() {
        if (component instanceof HasSize comp) {
            comp.setWidthFull();
        }
        return this;
    }

    public ComponentBuilder<T> heightFull() {
        if (component instanceof HasSize comp) {
            comp.setHeightFull();
        }
        return this;
    }

    public ComponentBuilder<T> sizeFull() {
        if (component instanceof HasSize comp) {
            comp.setSizeFull();
        }
        return this;
    }

    public ComponentBuilder<T> icon(VaadinIcon icon) {
        if (component instanceof Button comp) {
            comp.setIcon(new Icon(icon));
        }
        return this;
    }

    public ComponentBuilder<T> placeholder(String placeholder) {
        if (component instanceof TextFieldBase<?, ?> comp) {
            comp.setPlaceholder(placeholder);
        }
        return this;
    }


    public ComponentBuilder<T> justifyBetween() {
        if (component instanceof FlexComponent comp) {
            comp.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        }
        return this;
    }

    public ComponentBuilder<T> justifyAround() {
        if (component instanceof FlexComponent comp) {
            comp.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
        }
        return this;
    }

    public ComponentBuilder<T> justifyEvenly() {
        if (component instanceof FlexComponent comp) {
            comp.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        }
        return this;
    }

    public ComponentBuilder<T> justifyEnd() {
        if (component instanceof FlexComponent comp) {
            comp.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        }
        return this;
    }

    public ComponentBuilder<T> flexWrap() {
        if (component instanceof FlexLayout comp) {
            comp.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        }
        return this;
    }

    public ComponentBuilder<T> alignEnd() {
        if (component instanceof FlexComponent comp) {
            comp.setAlignItems(FlexComponent.Alignment.END);
        }
        return this;
    }

    public ComponentBuilder<T> alignCenter() {
        if (component instanceof FlexComponent comp) {
            comp.setAlignItems(FlexComponent.Alignment.CENTER);
        }
        if (component instanceof HasText) {
            component.getStyle().set("text-align", "center");
        }
        return this;
    }

    public ComponentBuilder<T> columnDirection() {
        if (component instanceof FlexLayout comp) {
            comp.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        }
        return this;
    }

    public T build() {
        return this.component;
    }
}
