package com.alelk.bcpt.importer.persist;

import com.alelk.bcpt.common.process.ProcessState;
import com.alelk.bcpt.common.process.Progress;
import com.alelk.bcpt.database.service.*;
import com.alelk.bcpt.importer.exception.BcptImporterException;
import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.util.Messages;
import com.alelk.bcpt.model.dto.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Parsed Bundle Saver
 *
 * Created by Alex Elkin on 06.10.2017.
 */
@Component
public class BcptDtoBundleSaver {

    private Messages messages;
    private UniversalService universalService;

    public BcptDtoBundleSaver(Messages messages) {
        this.messages = messages;
    }

    public void setUniversalService(UniversalService universalService) {
        this.universalService = universalService;
    }

    @SuppressWarnings("unchecked")
    public Flowable<Progress<BcptDtoBundle>> save(BcptDtoBundle bundle) {
        return Flowable.create((FlowableEmitter<Progress<BcptDtoBundle>> e) -> {
            final BcptDtoBundle bcptDtoBundle = new BcptDtoBundle();
            final Progress<BcptDtoBundle> result = new Progress<>(bcptDtoBundle, 0.0, ProcessState.IN_PROGRESS, new ArrayList<>());
            try {
                if (bundle == null) throw new BcptImporterException("Incorrect argument provided: bundle=" + null);
                final long countObjects = countObjects(bundle);
                long[] objectsProcessed = {0};
                final Consumer<? extends BcptDto> onOneProcessed = dto -> {
                    objectsProcessed[0]++;
                    result.setProgress(objectsProcessed[0] * 100.0 / countObjects);
                    e.onNext(result);
                };
                if (bundle.getPersons() != null)
                    persistDtos(bundle.getPersons().values(),
                            bcptDtoBundle::addPerson,
                            result::addError,
                            (Consumer<PersonDto>) onOneProcessed
                    );
                if (bundle.getBloodDonations() != null)
                    persistDtos(bundle.getBloodDonations().values(),
                            bcptDtoBundle::addBloodDonation,
                            result::addError,
                            (Consumer<BloodDonationDto>) onOneProcessed
                    );
                if (bundle.getBloodInvoices() != null)
                    persistDtos(bundle.getBloodInvoices().values(),
                            bcptDtoBundle::addBloodInvoice,
                            result::addError,
                            (Consumer<BloodInvoiceDto>) onOneProcessed
                    );
                if (bundle.getBloodPools() != null)
                    persistDtos(bundle.getBloodPools().values(),
                            bcptDtoBundle::addBloodPool,
                            result::addError,
                            (Consumer<BloodPoolDto>) onOneProcessed
                    );
                if (bundle.getProductBatches() != null)
                    persistDtos(bundle.getProductBatches().values(),
                            bcptDtoBundle::addProductBatch,
                            result::addError,
                            (Consumer<ProductBatchDto>) onOneProcessed
                    );
                result.setProgress(100.0);
                result.setState(result.getErrors() != null && result.getErrors().size() > 0 ? ProcessState.WITH_WARNINGS : ProcessState.SUCCESS);
                e.onNext(result);
                e.onComplete();

            } catch (Exception exc) {
                Exception exception = new BcptImporterException(messages.get("saver.unableSave", exc.getLocalizedMessage()));
                result.addError(exception);
                result.setState(ProcessState.FAILED);
                e.onNext(result);
                e.onError(exception);
            }

        }, BackpressureStrategy.LATEST).sample(1, TimeUnit.SECONDS, true).share();
    }

    private <T extends BcptDto> void persistDtos(Collection<T> dtos, Consumer<T> onPersist, Consumer<Throwable> onError, Consumer<T> onOneProcessed) throws BcptImporterException {
        for (T dto : dtos) {
            try {
                T persistedDto = persist(dto);
                onPersist.accept(persistedDto);
            } catch (Exception exc) {
                onError.accept(new BcptImporterException(messages.get("saver.unablePersistObject", exc.getLocalizedMessage(), dto)));
            }
            onOneProcessed.accept(dto);
        }
    }

    private <DTO extends BcptDto> DTO persist (DTO dto) {
        if (!universalService.isExternalIdExists(dto.getClass(), dto.getExternalId()))
            return universalService.create(dto);
        return universalService.update(dto.getExternalId(), dto, false, true);
    }

    private long countObjects(BcptDtoBundle bundle) {
        return (long) (bundle.getPersons() == null ? 0 : bundle.getPersons().size()) +
                (bundle.getBloodDonations() == null ? 0 : bundle.getBloodDonations().size()) +
                (bundle.getBloodInvoices() == null ? 0 : bundle.getBloodInvoices().size()) +
                (bundle.getBloodPools() == null ? 0 : bundle.getBloodPools().size()) +
                (bundle.getProductBatches() == null ? 0 : bundle.getProductBatches().size());
    }
}
