package com.alelk.bcpt.restapi.util;

import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.SortBy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Rest Api Util
 *
 * Created by Alex Elkin on 20.10.2017.
 */
public class RestApiUtil {

    public static List<SortBy> parseSortParams(List<String> sortBy) {
        return sortBy == null ? null
                : sortBy.stream().map(SortBy::parse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<Filter> parseFilterParams(List<String> filters) {
        return filters == null ? null
                : filters.stream().map(Filter::parse).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
