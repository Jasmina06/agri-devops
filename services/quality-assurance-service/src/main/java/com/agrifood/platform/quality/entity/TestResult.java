package com.agrifood.platform.quality.entity;

import com.agrifood.platform.quality.enums.ResultStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_test_id")
    private QualityTest qualityTest;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_parameter_id")
    private TestParameter testParameter;
    
    private String measuredValue;
    
    private String testCondition;
    
    private LocalDateTime testTime;
    
    @Enumerated(EnumType.STRING)
    private ResultStatus status;
    
    @Column(columnDefinition = "TEXT")
    private String observations;
    
    // Constructors
    public TestResult() {}
    
    public TestResult(TestParameter testParameter, String measuredValue, String testCondition) {
        this.testParameter = testParameter;
        this.measuredValue = measuredValue;
        this.testCondition = testCondition;
        this.testTime = LocalDateTime.now();
        this.status = ResultStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public QualityTest getQualityTest() {
        return qualityTest;
    }
    
    public void setQualityTest(QualityTest qualityTest) {
        this.qualityTest = qualityTest;
    }
    
    public TestParameter getTestParameter() {
        return testParameter;
    }
    
    public void setTestParameter(TestParameter testParameter) {
        this.testParameter = testParameter;
    }
    
    public String getMeasuredValue() {
        return measuredValue;
    }
    
    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
    }
    
    public String getTestCondition() {
        return testCondition;
    }
    
    public void setTestCondition(String testCondition) {
        this.testCondition = testCondition;
    }
    
    public LocalDateTime getTestTime() {
        return testTime;
    }
    
    public void setTestTime(LocalDateTime testTime) {
        this.testTime = testTime;
    }
    
    public ResultStatus getStatus() {
        return status;
    }
    
    public void setStatus(ResultStatus status) {
        this.status = status;
    }
    
    public String getObservations() {
        return observations;
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
    }
}