package com.agrifood.platform.quality.controller;

import com.agrifood.platform.quality.entity.QualityTest;
import com.agrifood.platform.quality.service.QualityTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quality")
public class QualityTestController {
    
    @Autowired
    private QualityTestService qualityTestService;
    
    @GetMapping
    public ResponseEntity<List<QualityTest>> getAllQualityTests() {
        List<QualityTest> qualityTests = qualityTestService.getAllQualityTests();
        return ResponseEntity.ok(qualityTests);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<QualityTest> getQualityTestById(@PathVariable Long id) {
        return qualityTestService.getQualityTestById(id)
                .map(test -> ResponseEntity.ok(test))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<QualityTest> createQualityTest(@RequestBody QualityTest qualityTest) {
        try {
            QualityTest createdTest = qualityTestService.createQualityTest(qualityTest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<QualityTest> updateQualityTest(@PathVariable Long id, @RequestBody QualityTest qualityTestDetails) {
        try {
            QualityTest updatedTest = qualityTestService.updateQualityTest(id, qualityTestDetails);
            return ResponseEntity.ok(updatedTest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQualityTest(@PathVariable Long id) {
        try {
            qualityTestService.deleteQualityTest(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/start")
    public ResponseEntity<QualityTest> startTest(@PathVariable Long id) {
        try {
            QualityTest updatedTest = qualityTestService.startTest(id);
            return ResponseEntity.ok(updatedTest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<QualityTest> completeTest(@PathVariable Long id) {
        try {
            QualityTest updatedTest = qualityTestService.completeTest(id);
            return ResponseEntity.ok(updatedTest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/approve")
    public ResponseEntity<QualityTest> approveTest(@PathVariable Long id) {
        try {
            QualityTest updatedTest = qualityTestService.approveTest(id);
            return ResponseEntity.ok(updatedTest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/reject")
    public ResponseEntity<QualityTest> rejectTest(@PathVariable Long id) {
        try {
            QualityTest updatedTest = qualityTestService.rejectTest(id);
            return ResponseEntity.ok(updatedTest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<QualityTest>> getQualityTestsByStatus(@PathVariable com.agrifood.platform.quality.entity.TestStatus status) {
        List<QualityTest> tests = qualityTestService.getQualityTestsByStatus(status);
        return ResponseEntity.ok(tests);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<QualityTest>> getQualityTestsByCategory(@PathVariable com.agrifood.platform.quality.entity.TestCategory category) {
        List<QualityTest> tests = qualityTestService.getQualityTestsByCategory(category);
        return ResponseEntity.ok(tests);
    }
    
    @GetMapping("/outcome/{outcome}")
    public ResponseEntity<List<QualityTest>> getQualityTestsByOutcome(@PathVariable com.agrifood.platform.quality.entity.TestOutcome outcome) {
        List<QualityTest> tests = qualityTestService.getQualityTestsByOutcome(outcome);
        return ResponseEntity.ok(tests);
    }
}