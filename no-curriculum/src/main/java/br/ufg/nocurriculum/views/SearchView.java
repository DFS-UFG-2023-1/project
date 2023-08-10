package br.ufg.nocurriculum.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.io.Serial;

@Route("search")
@UIScope
@SpringComponent
public class SearchView extends StickedHeaderView {

    @Serial
    private static final long serialVersionUID = 3529222048659430711L;

    public SearchView() {
        setContent(new H1("Search"));
    }
}
