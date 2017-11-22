package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity_;
import org.springframework.stereotype.Component;

/**
 * Product Batch Specification
 *
 * Created by Alex Elkin on 26.10.2017.
 */
@Component
public class ProductBatchSpecification extends AbstractSpecifications<ProductBatchEntity, ProductBatchEntity_> {

    public Specification<ProductBatchEntity> batchNumberEqual(String batchNumber) {
        return valueEqual(ProductBatchEntity_.batchNumber, batchNumber);
    }

    public Specification<ProductBatchEntity> locationStartsWith(String location) {
        return stringFieldStartsWith(ProductBatchEntity_.location, location);
    }

    public Specification<ProductBatchEntity> batchAuthorStartsWith(String batchAuthor) {
        return stringFieldStartsWith(ProductBatchEntity_.batchAuthor, batchAuthor);
    }

    public Specification<ProductBatchEntity> productProviderStartsWith(String productProvider) {
        return stringFieldStartsWith(ProductBatchEntity_.productProvider, productProvider);
    }

    public Specification<ProductBatchEntity> productNameStartsWith(String productName) {
        return stringFieldStartsWith(ProductBatchEntity_.productName, productName);
    }
}
