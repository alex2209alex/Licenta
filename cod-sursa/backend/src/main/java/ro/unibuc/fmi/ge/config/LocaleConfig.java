package ro.unibuc.fmi.ge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig {
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver ahlr = new AcceptHeaderLocaleResolver();
        ahlr.setDefaultLocale(new Locale("ro"));

        return ahlr;
    }
}
