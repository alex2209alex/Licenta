package ro.unibuc.fmi.ge.learning;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class I18nController {
    private final MessageSource messageSource;

    public I18nController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping(path = "/message")
    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("avizare.maritima.lista", null, locale);
    }
}
