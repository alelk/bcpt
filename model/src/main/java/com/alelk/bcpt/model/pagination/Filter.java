package com.alelk.bcpt.model.pagination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter
 *
 * Created by Alex Elkin on 11.10.2017.
 */
public class Filter {
    private static final Pattern PATTERN = Pattern.compile("^['\"]?(?<fieldName>\\w+)['\"]?(:['\"]?(?<filter>[\\p{L}\\w]+)['\"]?)?$");

    private String fieldName;

    private String filter;

    public Filter(String fieldName, String filter) {
        this.fieldName = fieldName;
        this.filter = filter;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return '\'' + fieldName + "\':'"  + filter + '\'';
    }

    public static Filter parse(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (!matcher.find()) return null;
        return new Filter(matcher.group("fieldName"), matcher.group("filter"));
    }
}
