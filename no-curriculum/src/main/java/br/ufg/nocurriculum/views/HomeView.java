package br.ufg.nocurriculum.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class HomeView extends AppLayout {

    public HomeView() {
        var title = new H1("NoCurriculum");
        title.getStyle()
            .set("font-size", "var(--lumo-font-size-l)")
            .set("left", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute");

        var tabs = getTabs();

        addToNavbar(title, tabs);
    }

    private TabSheet getTabs() {
        var tabs = new TabSheet();
        tabs.getStyle().set("margin", "auto");
        tabs.add("Talentos", new TalentView());
        tabs.add("Empresas", new CompanyView());
        tabs.add("Quem Somos", new WhoWeAreView());
        return tabs;
    }

    private Tab createTab(String viewName) {
        var link = new RouterLink();
        link.add(viewName);
        link.setRoute(this.getClass());
        link.setTabIndex(-1);
        return new Tab(link);
    }
}
