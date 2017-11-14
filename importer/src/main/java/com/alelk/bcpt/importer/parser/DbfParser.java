package com.alelk.bcpt.importer.parser;

import com.alelk.bcpt.importer.exception.BcptImporterException;
import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.result.OperationResult;
import com.alelk.bcpt.importer.result.Result;
import com.alelk.bcpt.importer.util.Messages;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.RhFactor;
import com.alelk.bcpt.model.dto.BloodDonationDto;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;
import com.alelk.bcpt.model.dto.PersonDto;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Blood Centre CDBF exported txt file parser
 *
 * Created by Alex Elkin on 04.10.2017.
 */
@Component
public class DbfParser implements Parser {

    private static final String FILE_HEADER_REGEX = "^\\s*DATE_KR\\s*\\|\\s*SHTR_KOD\\s*\\|\\s*GR_KR\\s*\\|" +
            "\\s*RH_KR\\s*\\|\\s*KOD_PROD\\s*\\|\\s*KOL\\s*\\|\\s*ANTICOAG\\s*\\|\\s*KOD_DON\\s*\\|\\s*DATE_ISP\\s*\\|" +
            "\\s*FIO\\s*\\|\\s*NOMER_N\\s*\\|\\s*KARANT\\s*\\|\\s*DATE_OTV\\s*\\|\\s*TYPE_OTV\\s*\\|\\s*FROM_BR\\s*\\|" +
            "\\s*GL_ID\\s*$";
    private static final Pattern FILE_ROW_REGEX = Pattern.compile("^\\s*(?<donationDate>\\d{8})\\s*\\|" +
            "\\s*(?<donationId>\\d{14})\\s*\\|\\s*(?<bloodType>\\d)\\s*\\|\\s*(?<rh>\\d)\\s*\\|" +
            "\\s*(?<donationType>\\d{1,5})\\s*\\|\\s*(?<donationAmount>\\d{1,6})\\s*\\|" +
            "\\s*(?<anticoagulant>\\d+)\\s*\\|\\s*(?<donorId>\\d{1,20})\\s*\\|\\s*(?<expirationDate>\\d{8})\\s*\\|" +
            "\\s*(?<donorLastName>(\\p{L}|[-()]){0,30})\\s+(?<donorFirstName>(\\p{L}|[-()]){0,20})\\.?\\s+(?<donorMiddleName>(\\p{L}|[-()]){0,20})\\.?" +
            "\\s*\\|\\s*(?<bloodInvoiceId>\\d{4,20})\\s*\\|\\s*(?<isQuaranteened>[TF])?\\s*\\|\\s*(\\d{8})?\\s*\\|" +
            "\\s*(\\d{1,10}?)\\s*\\|\\s*(\\d)\\s*\\|\\s*(\\d{1,20})\\s*$");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYYMMDD");

    private Messages messages;

    @Autowired
    public DbfParser(Messages messages) {
        this.messages = messages;
    }


    @Override
    public Flowable<OperationResult<BcptDtoBundle>> parse(File file) {
        return Flowable.create( (FlowableEmitter<OperationResult<BcptDtoBundle>> e) -> {
            final BcptDtoBundle parsedBundle = new BcptDtoBundle();
            OperationResult<BcptDtoBundle> operationResult = new OperationResult<>(
                    parsedBundle, 0.0, Result.IN_PROGRESS, new ArrayList<>()
            );
            InputStreamReader inputStreamReader = null;
            try {
                if (file == null || !file.exists() || !file.isFile())
                    throw new BcptImporterException(messages.get("file.notFound", file == null ? null : file.getAbsolutePath()));
                e.onNext(operationResult);
                inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                final long fileSize = file.length();
                long symbolsRead = 0;
                final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = nextLine(bufferedReader);
                symbolsRead += line.length();
                if (!line.matches(FILE_HEADER_REGEX))
                    throw new BcptImporterException(messages.get("dbfFile.incorrectHeader", FILE_HEADER_REGEX, line.replaceAll("\\s{2,}", " ")));
                do {
                    line = nextLine(bufferedReader);
                    if (line == null) {
                        operationResult.setResult(operationResult.getErrors().size() == 0 ? Result.SUCCESS : Result.WITH_WARNINGS);
                        operationResult.setProgress(100.0);
                        e.onNext(operationResult);
                        e.onComplete();
                        break;
                    }
                    symbolsRead += (line.length() + 1);
                    operationResult.setProgress(symbolsRead * 100.0 / fileSize);
                    try {
                        parseRow(line, parsedBundle);
                    } catch (BcptImporterException exc) {
                        operationResult.addErrror(exc);
                        if (operationResult.getErrors().size() >= 200)
                            throw new BcptImporterException(messages.get("dbfFile.aLotOfErrors",
                                    operationResult.getErrors().stream().map(Throwable::getLocalizedMessage).distinct()
                            .limit(10).collect(Collectors.joining(" >> "))));
                    }
                    e.onNext(operationResult);
                } while (true);
            } catch (Exception exc) {
                Exception bcptExc = new BcptImporterException(messages.get(
                        "file.cannotRead", file != null ? file.getAbsolutePath() : null,
                        exc.getLocalizedMessage())
                );
                operationResult.addErrror(bcptExc);
                operationResult.setResult(Result.FAILED);
                e.onNext(operationResult);
                e.onError(bcptExc);
            } finally {
                if (inputStreamReader != null) try {
                    inputStreamReader.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }, BackpressureStrategy.LATEST).sample(1, TimeUnit.SECONDS, true).share();
    }

    private void parseRow(String line, BcptDtoBundle parsedBundle) throws BcptImporterException {
        Matcher matcher = FILE_ROW_REGEX.matcher(line);
        if (!matcher.matches()) throw new BcptImporterException(messages.get("dbfFile.incorrectLine", line.replaceAll("\\s{2,}", " "), "A line not match the pattern."));
        try {
            Date donationDate = parseDate(matcher.group("donationDate"));
            Date donationExpirationDate = parseDate(matcher.group("expirationDate"));
            String donationId = matcher.group("donationId");
            BloodType bloodType = parseBloodType(matcher.group("bloodType"));
            RhFactor rhFactor = parseRhFactor(matcher.group("rh"));
            Double donationAmount = Double.parseDouble(matcher.group("donationAmount"));
            String donorId = matcher.group("donorId");
            String donorLastName = matcher.group("donorLastName");
            String donorFirstName = matcher.group("donorFirstName");
            String donorMiddleName = matcher.group("donorMiddleName");
            String bloodInvoiceId = matcher.group("bloodInvoiceId");
            saveDonor(parsedBundle, bloodType, rhFactor, donorId, donorLastName, donorFirstName, donorMiddleName);
            saveDonation(parsedBundle, donationDate, donationExpirationDate, donationId, donationAmount, bloodInvoiceId, donorId);
            saveInvoice(parsedBundle, donationId, bloodInvoiceId);

        } catch (BcptImporterException exc) {
            throw new BcptImporterException(messages.get("dbfFile.incorrectLine", line.replaceAll("\\s{2,}", " "), exc.getLocalizedMessage()));
        }
    }

    private void saveInvoice(BcptDtoBundle parsedBundle, String donationId, String bloodInvoiceId) {
        BloodInvoiceDto bloodInvoice = parsedBundle.getBloodInvoice(bloodInvoiceId);
        if (bloodInvoice == null) {
            bloodInvoice = new BloodInvoiceDto();
            bloodInvoice.setExternalId(bloodInvoiceId);
        }
        Set<String> donationIds = bloodInvoice.getBloodDonations();
        if (donationIds == null) donationIds = new HashSet<>();
        donationIds.add(donationId);
        bloodInvoice.setBloodDonations(donationIds);
        parsedBundle.addBloodInvoice(bloodInvoice);
    }

    private void saveDonation(BcptDtoBundle parsedBundle, Date donationDate, Date donationExpirationDate, String donationId, Double donationAmount, String bloodInvoiceId, String donorId) {
        BloodDonationDto bloodDonation = parsedBundle.getBloodDonation(donationId);
        if (bloodDonation == null) bloodDonation = new BloodDonationDto();
        bloodDonation.setExternalId(donationId);
        bloodDonation.setDonationDate(donationDate);
        bloodDonation.setExpirationDate(donationExpirationDate);
        bloodDonation.setAmount(donationAmount);
        bloodDonation.setDonor(donorId);
        parsedBundle.addBloodDonation(bloodDonation);
    }

    private void saveDonor(BcptDtoBundle parsedBundle, BloodType bloodType, RhFactor rhFactor, String donorId, String donorLastName, String donorFirstName, String donorMiddleName) {
        PersonDto donor = parsedBundle.getPerson(donorId);
        if (donor == null) donor = new PersonDto();
        donor.setExternalId(donorId);
        donor.setBloodType(bloodType);
        donor.setRhFactor(rhFactor);
        donor.setFirstName(donorFirstName);
        donor.setLastName(donorLastName);
        donor.setMiddleName(donorMiddleName);
        parsedBundle.addPerson(donor);
    }

    private static BloodType parseBloodType(String string) {
        if (string.equals("1")) return BloodType.O;
        if (string.equals("2")) return BloodType.A;
        if (string.equals("3")) return BloodType.B;
        if (string.equals("4")) return BloodType.AB;
        return null;
    }

    private static RhFactor parseRhFactor(String string) {
        if (string.equals("1")) return RhFactor.POSITIVE;
        if (string.equals("2")) return RhFactor.NEGATIVE;
        return null;
    }

    private Date parseDate(String string) throws BcptImporterException {
        try {
            return DATE_FORMAT.parse(string);
        } catch (ParseException e) {
            throw new BcptImporterException(messages.get("dbfFile.incorrectDate", string));
        }
    }

    private static String nextLine(BufferedReader bufferedReader) throws IOException {
        String line;
        do {
            line = bufferedReader.readLine();
        } while (line != null && line.matches("^[\\s-]*$"));
        return line;
    }
}
