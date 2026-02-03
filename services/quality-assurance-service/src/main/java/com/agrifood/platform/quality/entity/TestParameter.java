package com.agrifood.platform.quality.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "test_parameters")
public class TestParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_test_id")
    private QualityTest qualityTest;
    
    @Column(nullable = false)
    private String parameterName;
    
    private String unitOfMeasure;
    
    private String specification;
    
    private String method;
    
    private BigDecimal minAcceptableValue;
    
    private BigDecimal maxAcceptableValue;
    
    private Boolean isMandatory;
    
    // Constructors
    public TestParameter() {}
    
    public TestParameter(String parameterName, String unitOfMeasure, String specification, 
                        String method, BigDecimal minAcceptableValue, BigDecimal maxAcceptableValue) {
        this.parameterName = parameterName;
        this.unitOfMeasure = unitOfMeasure;
        this.specification = specification;
        this.method = method;
        this.minAcceptableValue = minAcceptableValue;
        this.maxAcceptableValue = maxAcceptableValue;
        this.isMandatory = true;
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
    
    public String getParameterName() {
        return parameterName;
    }
    
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
    
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
    
    public String getSpecification() {
        return specification;
    }
    
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public BigDecimal getMinAcceptableValue() {
        return minAcceptableValue;
    }
    
    public void setMinAcceptableValue(BigDecimal minAcceptableValue) {
        this.minAcceptableValue = minAcceptableValue;
    }
    
    public BigDecimal getMaxAcceptableValue() {
        return maxAcceptableValue;
    }
    
    public void setMaxAcceptableValue(BigDecimal maxAcceptableValue) {
        this.maxAcceptableValue = maxAcceptableValue;
    }
    
    public Boolean getIsMandatory() {
        return isMandatory;
    }
    
    public void setIsMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }
}