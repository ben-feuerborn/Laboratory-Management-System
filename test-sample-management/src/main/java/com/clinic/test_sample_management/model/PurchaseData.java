package com.clinic.test_sample_management.model;

import java.util.List;

public class PurchaseData {
    private String patientName;
    private String patientId;
    private List<TestItem> tests;
    private int total;

    public PurchaseData() {}

    public PurchaseData(String patientName, String patientId, List<TestItem> tests, int total) {
        this.patientName = patientName;
        this.patientId = patientId;
        this.tests = tests;
        this.total = total;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<TestItem> getTests() {
        return tests;
    }

    public void setTests(List<TestItem> tests) {
        this.tests = tests;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
