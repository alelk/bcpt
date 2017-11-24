package com.alelk.bcpt.reportgenerator.report;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract Report
 *
 * Created by Alex Elkin on 24.11.2017.
 */
abstract class AbstractReport implements Report {

    private Map<String, Object> params;

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    public Object getParam(String key) {
        return params == null ? null : params.get(key);
    }

    void setParam(String key, Object value) {
        if (params == null) params = new LinkedHashMap<>();
        params.put(key, value);
    }

    void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
