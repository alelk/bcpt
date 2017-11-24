package com.alelk.bcpt.reportgenerator.datasource;

import com.alelk.bcpt.model.dto.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Blood Pools Data Source
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public class ProductBatchReportDataSource implements JRDataSource {

    private final static Pattern BLOOD_DONATION_EXTERNAL_ID_PATTERN = Pattern.compile("^(\\d{6})(\\d{6})(\\d{2})$");
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy");

    private ProductBatchDto productBatchDto;
    private List<BloodPoolDto> bloodPools;
    private List<BloodInvoiceDto> bloodInvoices;
    private List<BloodInvoiceSeriesDto> bloodInvoiceSeries;
    private List<BloodPoolAnalysisDto> bloodPoolAnalyzes;
    private int index = -1;

    public ProductBatchReportDataSource(ProductBatchDto productBatchDto, List<BloodPoolDto> bloodPools, List<BloodInvoiceDto> bloodInvoices, List<BloodInvoiceSeriesDto> bloodInvoiceSeries, List<BloodPoolAnalysisDto> bloodPoolAnalyzes) {
        this.productBatchDto = productBatchDto;
        this.bloodPoolAnalyzes = bloodPoolAnalyzes;
        this.bloodPools = new ArrayList<>();
        this.bloodInvoices = new ArrayList<>();
        this.bloodInvoiceSeries = new ArrayList<>();
        this.bloodPools.addAll(bloodPools);
        this.bloodInvoices.addAll(bloodInvoices);
        this.bloodInvoiceSeries.addAll(bloodInvoiceSeries);
        this.bloodPools.sort(Comparator.comparingInt(BloodPoolDto::getPoolNumber));
        this.bloodInvoices.sort(Comparator.comparing(BloodInvoiceDto::getExternalId));
        this.bloodInvoiceSeries.sort(Comparator.comparing(BloodInvoiceSeriesDto::getExternalId));
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
        final BloodPoolDto bloodPool = bloodPools.get(index);
        if (bloodPool == null) return null;
        if ("bloodPool".equals(fieldName)) return bloodPool;
        final List<String> bloodDonationIds = new ArrayList<>();
        bloodDonationIds.addAll(bloodPool.getBloodDonations());
        bloodDonationIds.sort(Comparator.naturalOrder());
        if ("bloodDonationIds".equals(fieldName)) {
            return bloodDonationIds.stream().map(bloodDonationId -> {
                Matcher matcher = BLOOD_DONATION_EXTERNAL_ID_PATTERN.matcher(bloodDonationId);
                if (!matcher.find()) return bloodDonationId;
                return matcher.group(1) + "<style isBold='true' size='9'>" + matcher.group(2) + "</style>" + matcher.group(3);
            }).collect(Collectors.joining("     "));
        }
        final List<BloodInvoiceDto> bloodInvoices = this.bloodInvoices.stream()
                .filter(bi -> bi.getBloodDonations() != null && bloodDonationIds.stream().anyMatch(bloodDonationId ->
                        bi.getBloodDonations().contains(bloodDonationId)
                )).collect(Collectors.toList());
        if ("bloodInvoiceIds".equals(fieldName)) {
            return bloodInvoices.stream().map(BloodInvoiceDto::getExternalId).collect(Collectors.joining(" "));
        }
        final List<BloodInvoiceSeriesDto> bloodInvoiceSeries = this.bloodInvoiceSeries.stream()
                .filter(bis -> bis.getBloodInvoices() != null && bloodInvoices.stream().anyMatch(bloodInvoice ->
                        bis.getBloodInvoices().contains(bloodInvoice.getExternalId())
                )).collect(Collectors.toList());
        if ("bloodInvoiceSeriesIds".equals(fieldName)) {
            return bloodInvoiceSeries.stream().map(BloodInvoiceSeriesDto::getExternalId).collect(Collectors.joining(" "));
        }
        if ("bloodInvoiceDates".equals(fieldName)) {
            List<Date> dates = bloodInvoiceSeries.stream().map(BloodInvoiceSeriesDto::getSeriesDate).collect(Collectors.toList());
            if (dates.size() == 0)
                dates = bloodInvoices.stream().map(BloodInvoiceDto::getDeliveryDate).collect(Collectors.toList());
            return dates.stream().filter(Objects::nonNull).distinct().map(ProductBatchReportDataSource::mapDateToSimpleString)
                    .collect(Collectors.joining(" "));
        }
        if ("bloodPoolAnalyzes".equals(fieldName)) {
            if (bloodPoolAnalyzes == null) return null;
            return bloodPoolAnalyzes.stream()
                    .filter(bpa -> bloodPool.getExternalId().equals(bpa.getExternalId()))
                    .findFirst().orElse(null);
        }
        return null;
    }

    private static String mapDateToSimpleString(Date date) {
        return date == null ? null : SIMPLE_DATE_FORMAT.format(date);
    }
}
