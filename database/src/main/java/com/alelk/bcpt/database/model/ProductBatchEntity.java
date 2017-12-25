package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.AnalysisConclusion;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.model.ProductBatchEntity.QUERY_FIND_ALL;
import static com.alelk.bcpt.database.util.RepositoryUtil.processAnalysisConclusion;

/**
 * Product Batch Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table(name = "productBatches")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select pbe from ProductBatchEntity pbe"
        )})
public class ProductBatchEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllProductBatches";

    @Sortable
    private Integer batchNumber;

    @Sortable
    private Date batchDate;

    @Sortable
    private String location;

    @Sortable
    private String batchAuthor;

    @Sortable
    private String productProvider;

    @Sortable
    private String productName;

    @OneToMany
    @JoinColumn(name = "productbatch_id")
    private Set<BloodPoolEntity> bloodPools;

    public ProductBatchEntity() {
    }

    public ProductBatchEntity(String externalId, Integer batchNumber, Date batchDate, String location, String batchAuthor, String productProvider, String productName, Set<BloodPoolEntity> bloodPools) {
        super(externalId);
        this.batchNumber = batchNumber;
        this.batchDate = batchDate;
        this.location = location;
        this.batchAuthor = batchAuthor;
        this.productProvider = productProvider;
        this.productName = productName;
        this.bloodPools = bloodPools;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public Set<BloodPoolEntity> getBloodPools() {
        return bloodPools;
    }

    public void setBloodPools(Set<BloodPoolEntity> bloodPools) {
        this.bloodPools = bloodPools;
    }

    public Double getTotalAmount() {
        if (bloodPools == null) return null;
        return bloodPools.stream().mapToDouble(BloodPoolEntity::getTotalAmount).sum();
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return bloodPools == null ? null :
                processAnalysisConclusion(bloodPools.stream().map(BloodPoolEntity::getAnalysisConclusion));
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBatchAuthor() {
        return batchAuthor;
    }

    public void setBatchAuthor(String batchAuthor) {
        this.batchAuthor = batchAuthor;
    }

    public String getProductProvider() {
        return productProvider;
    }

    public void setProductProvider(String productProvider) {
        this.productProvider = productProvider;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductBatchEntity{" +
                "batchNumber=" + batchNumber +
                ", batchDate=" + batchDate +
                ", location='" + location + '\'' +
                ", batchAuthor='" + batchAuthor + '\'' +
                ", productProvider='" + productProvider + '\'' +
                ", productName='" + productName + '\'' +
                ", bloodPools=[" + (bloodPools == null ? "" : bloodPools.stream().map(BloodPoolEntity::getExternalId).collect(Collectors.joining(", "))) +
                "]}";
    }
}
