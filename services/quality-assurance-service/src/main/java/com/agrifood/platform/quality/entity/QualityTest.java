package com.agrifood.platform.quality.entity;

import com.agrifood.platform.quality.enums.TestType;
import com.agrifood.platform.quality.enums.TestCategory;
import com.agrifood.platform.quality.enums.TestStatus;
import com.agrifood.platform.quality.enums.TestOutcome;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quality_tests")
public class QualityTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String testName;

    @Column(nullable = false)
    private String testCode;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TestType testType;

    @Enumerated(EnumType.STRING)
    private TestCategory category;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tested_item_id")
    private com.agrifood.platform.inventory.entity.InventoryItem testedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conducted_by_id")
    private com.agrifood.platform.farmer.entity.Farmer conductedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_id")
    private com.agrifood.platform.supplier.entity.Supplier verifiedBy;

    private LocalDateTime testDate;

    private LocalDateTime expiryDate;

    @Column(columnDefinition = "TEXT")
    private String testMethod;

    @Column(columnDefinition = "TEXT")
    private String testProcedure;

    @OneToMany(mappedBy = "qualityTest", cascade = CascadeType.ALL)
    private List<TestParameter> testParameters;

    @OneToMany(mappedBy = "qualityTest", cascade = CascadeType.ALL)
    private List<TestResult> testResults;

    @Enumerated(EnumType.STRING)
    private TestOutcome outcome;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(columnDefinition = "TEXT")
    private String recommendations;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public QualityTest() {}

    public QualityTest(String testName, String testCode, TestType testType, TestCategory category,
                      com.agrifood.platform.inventory.entity.InventoryItem testedItem,
                      com.agrifood.platform.farmer.entity.Farmer conductedBy,
                      com.agrifood.platform.supplier.entity.Supplier verifiedBy,
                      LocalDateTime testDate) {
        this.testName = testName;
        this.testCode = testCode;
        this.testType = testType;
        this.category = category;
        this.testedItem = testedItem;
        this.conductedBy = conductedBy;
        this.verifiedBy = verifiedBy;
        this.testDate = testDate;
        this.status = TestStatus.SCHEDULED;
        this.outcome = TestOutcome.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public TestCategory getCategory() {
        return category;
    }

    public void setCategory(TestCategory category) {
        this.category = category;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public com.agrifood.platform.inventory.entity.InventoryItem getTestedItem() {
        return testedItem;
    }

    public void setTestedItem(com.agrifood.platform.inventory.entity.InventoryItem testedItem) {
        this.testedItem = testedItem;
    }

    public com.agrifood.platform.farmer.entity.Farmer getConductedBy() {
        return conductedBy;
    }

    public void setConductedBy(com.agrifood.platform.farmer.entity.Farmer conductedBy) {
        this.conductedBy = conductedBy;
    }

    public com.agrifood.platform.supplier.entity.Supplier getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(com.agrifood.platform.supplier.entity.Supplier verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getTestProcedure() {
        return testProcedure;
    }

    public void setTestProcedure(String testProcedure) {
        this.testProcedure = testProcedure;
    }

    public List<TestParameter> getTestParameters() {
        return testParameters;
    }

    public void setTestParameters(List<TestParameter> testParameters) {
        this.testParameters = testParameters;
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public TestOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(TestOutcome outcome) {
        this.outcome = outcome;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}