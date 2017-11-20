package com.alelk.bcpt.reportgenerator.datasource;

import com.alelk.bcpt.model.dto.BloodPoolDto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Blood Pools Data Source
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public class BloodPoolsDataSource implements JRDataSource {

    private List<BloodPoolDto> bloodPools;
    private int index = -1;

    public BloodPoolsDataSource(List<BloodPoolDto> bloodPools) {
        this.bloodPools = new ArrayList<>();
        this.bloodPools.addAll(bloodPools);
        this.bloodPools.sort(Comparator.comparingInt(BloodPoolDto::getPoolNumber));
    }

    @Override
    public boolean next() throws JRException {
        return ++index < bloodPools.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (jrField == null) return null;
        final String fieldName = jrField.getName();
        if ("bloodPool".equals(fieldName)) return bloodPools.get(index);
        return null;
    }
}
