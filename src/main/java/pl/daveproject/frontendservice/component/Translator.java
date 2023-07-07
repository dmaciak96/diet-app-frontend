package pl.daveproject.frontendservice.component;

import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.internal.LocaleUtil;

import java.util.Optional;

public interface Translator {
    default String getTranslation(String key, Object... params) {
        Optional<I18NProvider> i18NProvider = LocaleUtil.getI18NProvider();
        return i18NProvider.map((i18n) ->
                        i18n.getTranslation(key, LocaleUtil.getLocale(() -> i18NProvider), params))
                .orElseGet(() -> "!{" + key + "}!");
    }
}
