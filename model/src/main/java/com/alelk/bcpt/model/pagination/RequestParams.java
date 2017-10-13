package com.alelk.bcpt.model.pagination;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Request Params
 *
 * Created by Alex Elkin on 11.10.2017.
 */
public class RequestParams {

    private Map<String, SortDirection> sortBy;

    private Map<String, String> filters;

    public Map<String, SortDirection> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Map<String, SortDirection> sortBy) {
        this.sortBy = sortBy;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "RequestParams{" +
                "sortBy=" + (sortBy != null ? '[' + sortBy.keySet().stream()
                .map(key -> key + "->" + sortBy.get(key)).collect(Collectors.joining(", ")) + ']' : null) +
                ", filters=" + (filters != null ? '[' + filters.keySet().stream()
                .map(key -> key + "->" + filters.get(key)).collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
