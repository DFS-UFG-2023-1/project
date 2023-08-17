package br.ufg.nocurriculum.events;


import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

import java.io.Serial;

public class FlexLayoutDeselectEvent extends ComponentEvent<FlexLayout> {

    @Serial
    private static final long serialVersionUID = 7746922092074906832L;


    public FlexLayoutDeselectEvent(FlexLayout source) {
        super(source, false);
    }

}
