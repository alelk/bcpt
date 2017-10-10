package com.alelk.bcpt.importer.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Messages
 *
 * Created by Alex Elkin on 05.10.2017.
 */
@Component
public class Messages {

    private ApplicationContext context;
    private Locale locale = Locale.ENGLISH;

    Messages(ApplicationContext context) {
        this.context = context;
    }

    public String get(String code) {
        return get(code, new Object[]{});
    }

    public String get(String code, Object argument1) {
        return get(code, new Object[]{argument1});
    }

    public String get(String code, Object argument1, Object argument2) {
        return get(code, new Object[]{argument1, argument2});
    }

    public String get(String code, Object[] arguments) {
        return get(code, arguments, locale);
    }

    public String get(String code, Object[] arguments, Locale locale) {
        return context.getMessage(code, arguments, locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
