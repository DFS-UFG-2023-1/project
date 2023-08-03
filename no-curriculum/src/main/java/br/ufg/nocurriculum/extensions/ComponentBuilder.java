package br.ufg.nocurriculum.extensions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class ComponentBuilder<T extends Component> {

    private final T component;

    private ComponentBuilder(T component) {
        this.component = component;
    }

    public static <T extends Component> ComponentBuilder<T> $(T component) {
        return new ComponentBuilder<>(component);
    }

    public ComponentBuilder<T> add(Component ... components) {
        if (component instanceof HasComponents comp) {
            comp.add(components);
        }
        return this;
    }

    public ComponentBuilder<T> text(String value) {
        if (component instanceof HasText comp) {
           comp.setText(value);
        }
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

    public ComponentBuilder<T> border(String value) {
        component.getStyle().set("border", value);
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

    public ComponentBuilder<T> widthFull() {
        if (component instanceof HasSize comp) {
            comp.setWidthFull();
        }
        return this;
    }

    public ComponentBuilder<T> icon(VaadinIcon icon) {
        if (component instanceof Button comp) {
            comp.setIcon(new Icon(icon));
        }
        return this;
    }

    public ComponentBuilder<T> iconAfterText() {
        if (component instanceof Button comp) {
            comp.setIconAfterText(true);
        }
        return this;
    }

    public ComponentBuilder<T> justifyBetween() {
        if (component instanceof FlexComponent comp) {
            comp.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
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
        return this;
    }

    public ComponentBuilder<T> alignStart() {
        if (component instanceof FlexComponent comp) {
            comp.setAlignItems(FlexComponent.Alignment.START);
        }
        return this;
    }

    public ComponentBuilder<T> alignContentStart() {
        if (component instanceof FlexLayout comp) {
            comp.setAlignContent(FlexLayout.ContentAlignment.START);
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
