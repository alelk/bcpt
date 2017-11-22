package com.alelk.bcpt.reportgenerator.datasource;

import com.alelk.bcpt.model.dto.BloodInvoiceDto;
import com.alelk.bcpt.model.dto.BloodPoolDto;
import com.alelk.bcpt.model.dto.ProductBatchDto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Blood Pools Data Source
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public class BloodPoolsDataSource implements JRDataSource {

    private final static Pattern BLOOD_DONATION_EXTERNAL_ID_PATTERN = Pattern.compile("^(\\d{6})(\\d{6})(\\d{2})$");

    private ProductBatchDto productBatchDto;
    private List<BloodPoolDto> bloodPools;
    private List<BloodInvoiceDto> bloodInvoices;
    private int index = -1;

    public BloodPoolsDataSource(ProductBatchDto productBatchDto, List<BloodPoolDto> bloodPools, List<BloodInvoiceDto> bloodInvoices) {
        this.productBatchDto = productBatchDto;
        this.bloodPools = new ArrayList<>();
        this.bloodInvoices = new ArrayList<>();
        this.bloodPools.addAll(bloodPools);
        this.bloodInvoices.addAll(bloodInvoices);
        this.bloodPools.sort(Comparator.comparingInt(BloodPoolDto::getPoolNumber));
        this.bloodInvoices.sort(Comparator.comparing(BloodInvoiceDto::getExternalId));
    }

    @Override
    public boolean next() throws JRException {
        return ++index < bloodPools.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (jrField == null) return null;
        final String fieldName = jrField.getName();
        if ("productBatch".equals(fieldName)) return productBatchDto;
        if ("bloodPool".equals(fieldName)) return bloodPools.get(index);
        final List<String> bloodDonationIds = new ArrayList<>();
        bloodDonationIds.addAll(bloodPools.get(index).getBloodDonations());
        bloodDonationIds.sort(Comparator.naturalOrder());
        if ("bloodDonationIds".equals(fieldName)) {
            if (bloodPools.get(index) == null) return null;
            return bloodDonationIds.stream().map(bloodDonationId -> {
                Matcher matcher = BLOOD_DONATION_EXTERNAL_ID_PATTERN.matcher(bloodDonationId);
                if (!matcher.find()) return bloodDonationId;
                return matcher.group(1) + "<style isBold='true' size='9'>" + matcher.group(2) + "</style>" + matcher.group(3);
            }).collect(Collectors.joining(" ")) + " ";
        }
        if ("bloodInvoiceIds".equals(fieldName)) {
            return bloodInvoices.stream()
                    .filter(bi ->
                            bi.getBloodDonations() != null && bloodDonationIds.stream().anyMatch(bloodDonationId ->
                                    bi.getBloodDonations().contains(bloodDonationId)
                            )
                    ).map(BloodInvoiceDto::getExternalId).collect(Collectors.joining(" "));
        }
        return null;
    }
}
