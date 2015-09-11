package tw.edu.ntu.im.sdm.sdmtools.devconsole.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Locale;

import tw.edu.ntu.im.sdm.sdmtools.devconsole.ui.views.LoginView;

@Theme("dashboard")
@Title("Developer Console")
@SuppressWarnings("serial")
public final class DashboardUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DashboardUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        setLocale(Locale.UK);

        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();
    }

    private void updateContent() {
        setContent(new LoginView());
        addStyleName("loginview");
    }

}
