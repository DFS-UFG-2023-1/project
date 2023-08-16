package br.ufg.nocurriculum.i18n;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.internal.JsonSerializer;
import elemental.json.JsonFactory;
import elemental.json.JsonValue;
import elemental.json.impl.JreJsonFactory;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoginI18nParser {

    private LoginI18nParser() {
    }

    private static final JsonValue CUSTOM_LOGIN_I18N;

    static {
        try {
            final JsonFactory JSON_FACTORY = new JreJsonFactory();
            CUSTOM_LOGIN_I18N = JSON_FACTORY.parse(
                IOUtils.resourceToString("/login-i18n.json", StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static LoginI18n get() {
        return JsonSerializer.toObject(LoginI18n.class, CUSTOM_LOGIN_I18N);
    }

}
