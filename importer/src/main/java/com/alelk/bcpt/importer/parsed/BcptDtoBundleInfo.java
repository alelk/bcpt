package com.alelk.bcpt.importer.parsed;

/**
 * Bcpt DTO Bundle Info
 *
 * Created by Alex Elkin on 10.11.2017.
 */
public class BcptDtoBundleInfo {

    private int countPersons;
    private int countBloodDonations;
    private int countBloodInvoices;
    private int countBloodPools;
    private int countProductBatches;

    public BcptDtoBundleInfo() {
    }

    public BcptDtoBundleInfo(int countPersons, int countBloodDonations, int countBloodInvoices, int countBloodPools, int countProductBatches) {
        this.countPersons = countPersons;
        this.countBloodDonations = countBloodDonations;
        this.countBloodInvoices = countBloodInvoices;
        this.countProductBatches = countProductBatches;
        this.countBloodPools = countBloodPools;
    }

    public int getCountPersons() {
        return countPersons;
    }

    public void setCountPersons(int countPersons) {
        this.countPersons = countPersons;
    }

    public int getCountBloodDonations() {
        return countBloodDonations;
    }

    public void setCountBloodDonations(int countBloodDonations) {
        this.countBloodDonations = countBloodDonations;
    }

    public int getCountBloodInvoices() {
        return countBloodInvoices;
    }

    public void setCountBloodInvoices(int countBloodInvoices) {
        this.countBloodInvoices = countBloodInvoices;
    }

    public int getCountProductBatches() {
        return countProductBatches;
    }

    public void setCountProductBatches(int countProductBatches) {
        this.countProductBatches = countProductBatches;
    }

    public int getCountBloodPools() {
        return countBloodPools;
    }

    public void setCountBloodPools(int countBloodPools) {
        this.countBloodPools = countBloodPools;
    }

    public static BcptDtoBundleInfo fromBundle(BcptDtoBundle bundle) {
        return new BcptDtoBundleInfo(
                bundle == null || bundle.getPersons() == null ? 0 : bundle.getPersons().size(),
                bundle == null || bundle.getBloodDonations() == null ? 0 : bundle.getBloodDonations().size(),
                bundle == null || bundle.getBloodInvoices() == null ? 0 : bundle.getBloodInvoices().size(),
                bundle == null || bundle.getBloodPools() == null ? 0 : bundle.getBloodPools().size(),
                bundle == null || bundle.getProductBatches() == null ? 0 : bundle.getProductBatches().size()
        );
    }

    @Override
    public String toString() {
        return "BcptDtoBundleInfo{" +
                "countPersons=" + countPersons +
                ", countBloodDonations=" + countBloodDonations +
                ", countBloodInvoices=" + countBloodInvoices +
                ", countProductBatches=" + countProductBatches +
                ", countBloodPools=" + countBloodPools +
                '}';
    }
}
