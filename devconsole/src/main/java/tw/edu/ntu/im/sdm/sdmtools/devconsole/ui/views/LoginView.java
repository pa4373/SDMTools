package tw.edu.ntu.im.sdm.sdmtools.devconsole.ui.views;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Iterator;

import tw.edu.ntu.im.sdm.sdmtools.devconsole.ui.components.ConfirmPasswordValidator;
import tw.edu.ntu.im.sdm.sdmtools.devconsole.ui.components.NTUEmailValidator;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();

        Component form = buildForm();
        addComponent(form);
        setComponentAlignment(form, Alignment.MIDDLE_CENTER);

        Notification notification = new Notification("Welcome to Developer Console");
        notification
                .setDescription("If you're a newcomer or lost your password," +
                        " fill your email (<i>ntu.edu.tw</i> domain only) and password." +
                        " An mail with activation instructions will be sent to your mailbox.");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());
    }

    private Component buildForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildSignInFields());
        loginPanel.addComponent(buildSignUpFields());
        return loginPanel;
    }

    private Component buildSignInFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("username", new ObjectProperty<>(""));
        item.addItemProperty("password", new ObjectProperty<>(""));

        final FieldGroup binder = new FieldGroup(item);

        final TextField username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.setRequired(true);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        binder.bind(username, "username");

        final PasswordField password = new PasswordField("Password");
        password.setIcon(FontAwesome.LOCK);
        password.setRequired(true);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        binder.bind(password, "password");

        final Button signIn = new Button("Sign In");
        signIn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signIn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signIn.focus();

        fields.addComponents(username, password, signIn);
        fields.setComponentAlignment(signIn, Alignment.BOTTOM_LEFT);

        signIn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                try {
                    binder.commit();
                    Notification.show("It's working!");
                } catch (FieldGroup.CommitException e) {
                    Iterator<Field<?>> it = e.getInvalidFields().keySet().iterator();
                    if (it.hasNext()) {
                        it.next().focus();
                    }
                }
            }
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("Developer Console");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

    private Component buildSignUpFields() {
        final PropertysetItem item = new PropertysetItem();
        item.addItemProperty("email", new ObjectProperty<>(""));
        item.addItemProperty("password", new ObjectProperty<>(""));
        item.addItemProperty("password2", new ObjectProperty<>(""));
        final FieldGroup binder = new FieldGroup(item);
        binder.setBuffered(true);

        VerticalLayout outerFields = new VerticalLayout();
        outerFields.setSpacing(true);
        outerFields.addStyleName("fields");

        HorizontalLayout innerFields = new HorizontalLayout();
        innerFields.setSpacing(true);
        innerFields.addStyleName("fields");

        String ntuMailOnly = "ntu.edu.tw domain only.";

        Label signUpLabel = new Label("Sign-up");
        signUpLabel.setSizeUndefined();
        signUpLabel.addStyleName(ValoTheme.LABEL_H4);
        signUpLabel.addStyleName(ValoTheme.LABEL_COLORED);

        final TextField email = new TextField("Email");
        email.setSizeFull();
        email.setIcon(FontAwesome.ENVELOPE);
        email.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        email.setInputPrompt(ntuMailOnly);
        email.setRequired(true);
        email.addValidator(new NTUEmailValidator(ntuMailOnly));
        binder.bind(email, "email");

        final PasswordField password = new PasswordField("Set Password");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setRequired(true);
        binder.bind(password, "password");

        final PasswordField password2 = new PasswordField("Confirm Password");
        password2.setIcon(FontAwesome.LOCK);
        password2.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password2.addValidator(new ConfirmPasswordValidator("Passwords don't match.", password));
        password2.setRequired(true);
        binder.bind(password2, "password2");

        final Button signUp = new Button("Sign Up");
        signUp.addStyleName(ValoTheme.BUTTON_PRIMARY);
        innerFields.addComponents(password, password2, signUp);
        innerFields.setComponentAlignment(signUp, Alignment.BOTTOM_LEFT);
        outerFields.addComponents(signUpLabel, email, innerFields);

        signUp.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                try {
                    binder.commit();
                    Notification.show("It's working.");
                } catch (FieldGroup.CommitException e) {
                    Iterator<Field<?>> it = e.getInvalidFields().keySet().iterator();
                    if (it.hasNext()) {
                        it.next().focus();
                    }
                }
            }
        });
        return outerFields;
    }
}
