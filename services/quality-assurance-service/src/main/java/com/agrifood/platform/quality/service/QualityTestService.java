package com.agrifood.platform.quality.service;

import com.agrifood.platform.quality.entity.*;
import com.agrifood.platform.quality.repository.QualityTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualityTestService {
    
    @Autowired
    private QualityTestRepository qualityTestRepository;
    
    public List<QualityTest> getAllQualityTests() {
        return qualityTestRepository.findAll();
    }
    
    public Optional<QualityTest> getQualityTestById(Long id) {
        return qualityTestRepository.findById(id);
    }
    
    public QualityTest createQualityTest(QualityTest qualityTest) {
        // Validate quality test data
        validateQualityTest(qualityTest);
        
        // Set initial status
        qualityTest.setStatus(com.agrifood.platform.quality.enums.TestStatus.SCHEDULED);
        qualityTest.setOutcome(com.agrifood.platform.quality.enums.TestOutcome.PENDING);
        
        return qualityTestRepository.save(qualityTest);
    }
    
    public QualityTest updateQualityTest(Long id, QualityTest qualityTestDetails) {
        QualityTest qualityTest = qualityTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality test not found with id: " + id));
        
        // Update fields
        qualityTest.setTestName(qualityTestDetails.getTestName());
        qualityTest.setTestCode(qualityTestDetails.getTestCode());
        qualityTest.setDescription(qualityTestDetails.getDescription());
        qualityTest.setTestType(qualityTestDetails.getTestType());
        qualityTest.setCategory(qualityTestDetails.getCategory());
        qualityTest.setTestedItem(qualityTestDetails.getTestedItem());
        qualityTest.setConductedBy(qualityTestDetails.getConductedBy());
        qualityTest.setVerifiedBy(qualityTestDetails.getVerifiedBy());
        qualityTest.setTestDate(qualityTestDetails.getTestDate());
        qualityTest.setExpiryDate(qualityTestDetails.getExpiryDate());
        qualityTest.setTestMethod(qualityTestDetails.getTestMethod());
        qualityTest.setTestProcedure(qualityTestDetails.getTestProcedure());
        qualityTest.setTestParameters(qualityTestDetails.getTestParameters());
        qualityTest.setTestResults(qualityTestDetails.getTestResults());
        qualityTest.setRemarks(qualityTestDetails.getRemarks());
        qualityTest.setRecommendations(qualityTestDetails.getRecommendations());
        qualityTest.setUpdatedAt(java.time.LocalDateTime.now());
        
        return qualityTestRepository.save(qualityTest);
    }
    
    public void deleteQualityTest(Long id) {
        QualityTest qualityTest = qualityTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality test not found with id: " + id));
        qualityTestRepository.delete(qualityTest);
    }
    
    public QualityTest startTest(Long id) {
        QualityTest qualityTest = qualityTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality test not found with id: " + id));
        
        if (qualityTest.getStatus() != TestStatus.SCHEDULED) {
            throw new RuntimeException("Quality test must be in scheduled status to start");
        }
        
        qualityTest.setStatus(TestStatus.IN_PROGRESS);
        qualityTest.setUpdatedAt(java.time.LocalDateTime.now());
        
        return qualityTestRepository.save(qualityTest);
    }
    
    public QualityTest completeTest(Long id) {
        QualityTest qualityTest = qualityTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality test not found with id: " + id));
        
        if (qualityTest.getStatus() != TestStatus.IN_PROGRESS) {
            throw new RuntimeException("Quality test must be in progress to complete");
        }
        
        // Calculate outcome based on test results
        TestOutcome outcome = calculateTestOutcome(qualityTest);
        
        qualityTest.setStatus(TestStatus.COMPLETED);
        qualityTest.setOutcome(outcome);
        qualityTest.setUpdatedAt(java.time.LocalDateTime.now());
        
        return qualityTestRepository.save(qualityTest);
    }
    
    public QualityTest approveTest(Long id) {
        QualityTest qualityTest = qualityTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality test not found with id: " + id));
        
        if (qualityTest.getStatus() != TestStatus.COMPLETED) {
            throw new RuntimeException("Quality test must be completed to approve");
        }
        
        qualityTest.setStatus(TestStatus.APPROVED);
        qualityTest.setUpdatedAt(java.time.LocalDateTime.now());
        
        return qualityTestRepository.save(qualityTest);
    }
    
    public QualityTest rejectTest(Long id) {
        QualityTest qualityTest = qualityTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality test not found with id: " + id));
        
        if (qualityTest.getStatus() != TestStatus.COMPLETED) {
            throw new RuntimeException("Quality test must be completed to reject");
        }
        
        qualityTest.setStatus(TestStatus.REJECTED);
        qualityTest.setOutcome(TestOutcome.FAILED);
        qualityTest.setUpdatedAt(java.time.LocalDateTime.now());
        
        return qualityTestRepository.save(qualityTest);
    }
    
    public List<QualityTest> getQualityTestsByStatus(TestStatus status) {
        return qualityTestRepository.findByStatus(status);
    }
    
    public List<QualityTest> getQualityTestsByCategory(TestCategory category) {
        return qualityTestRepository.findByCategory(category);
    }
    
    public List<QualityTest> getQualityTestsByOutcome(TestOutcome outcome) {
        return qualityTestRepository.findByOutcome(outcome);
    }
    
    private TestOutcome calculateTestOutcome(QualityTest qualityTest) {
        // Simple logic to determine outcome based on test results
        // In a real system, this would be more complex
        boolean allPassed = true;
        
        for (TestResult result : qualityTest.getTestResults()) {
            if (result.getStatus() == ResultStatus.REJECTED) {
                allPassed = false;
                break;
            }
        }
        
        return allPassed ? TestOutcome.PASSED : TestOutcome.FAILED;
    }
    
    private void validateQualityTest(QualityTest qualityTest) {
        if (qualityTest.getTestName() == null || qualityTest.getTestName().trim().isEmpty()) {
            throw new RuntimeException("Test name is required");
        }
        if (qualityTest.getTestCode() == null || qualityTest.getTestCode().trim().isEmpty()) {
            throw new RuntimeException("Test code is required");
        }
        if (qualityTest.getTestType() == null) {
            throw new RuntimeException("Test type is required");
        }
        if (qualityTest.getCategory() == null) {
            throw new RuntimeException("Test category is required");
        }
        if (qualityTest.getTestedItem() == null) {
            throw new RuntimeException("Tested item is required");
        }
        if (qualityTest.getConductedBy() == null) {
            throw new RuntimeException("Conducted by farmer is required");
        }
        if (qualityTest.getTestDate() == null) {
            throw new RuntimeException("Test date is required");
        }
    }
}