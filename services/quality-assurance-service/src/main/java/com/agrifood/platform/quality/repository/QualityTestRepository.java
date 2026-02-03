package com.agrifood.platform.quality.repository;

import com.agrifood.platform.quality.entity.QualityTest;
import com.agrifood.platform.quality.enums.TestCategory;
import com.agrifood.platform.quality.enums.TestOutcome;
import com.agrifood.platform.quality.enums.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityTestRepository extends JpaRepository<QualityTest, Long> {
    List<QualityTest> findByStatus(TestStatus status);
    List<QualityTest> findByCategory(TestCategory category);
    List<QualityTest> findByOutcome(TestOutcome outcome);
}