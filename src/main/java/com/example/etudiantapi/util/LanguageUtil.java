package com.example.etudiantapi.util;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LanguageUtil {

    private static final Logger LOGGER = Logger.getLogger(LanguageUtil.class.getName());
    private static final String BUNDLE_NAME = "messages";
    private static final String DEFAULT_LANGUAGE = "fr";

    public String getMessage(String key, String language) {
        try {
            String lang = (language != null && !language.trim().isEmpty()) ? language : DEFAULT_LANGUAGE;
            Locale locale = new Locale(lang);
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
            return bundle.getString(key);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Impossible de récupérer le message pour la clé: " + key + ", langue: " + language, e);
            return key; // Retourne la clé si le message n'est pas trouvé
        }
    }

    public String getMessage(String key) {
        return getMessage(key, DEFAULT_LANGUAGE);
    }

    public boolean isLanguageSupported(String language) {
        try {
            Locale locale = new Locale(language);
            ResourceBundle.getBundle(BUNDLE_NAME, locale);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getSupportedLanguages() {
        return new String[]{"fr", "en"};
    }
}
