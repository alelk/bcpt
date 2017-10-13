package com.alelk.bcpt.model.pagination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SortBy
 *
 * Created by Alex Elkin on 11.10.2017.
 */
public class SortBy {
    private static final Pattern PATTERN = Pattern.compile("^['\"]?(?<fieldName>\\w+)['\"]?(:['\"]?(?<sortDirection>desc|asc)['\"]?)?$");

    private String fieldName;

    private SortDirection direction;

    public SortBy(String fieldName, SortDirection direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return '\'' + fieldName + "\'->"  + direction;
    }

    public static SortBy parse(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (!matcher.find()) return null;
        return new SortBy(matcher.group("fieldName"), SortDirection.forSignature(matcher.group("sortDirection")));
    }
}
