package com.usmobile.assessment.cycle_usage_service.repository;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillingCycleRepositoryTest {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    private final BillingCycleRepository billingCycleRepository;

    @Autowired
    public BillingCycleRepositoryTest(BillingCycleRepository billingCycleRepository) {
        this.billingCycleRepository = billingCycleRepository;
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @BeforeEach
    public void setUp() {
        billingCycleRepository.saveAll(createMockBillingCycles());
    }

    private List<BillingCycle> createMockBillingCycles() {
        Date startDate1 = new Date(1727740800); // 10/01/2024 00:00:00-0400
        Date endDate1 = new Date(1730332800); // 10/31/2024 00:00:00-0400

        Date startDate2 = new Date(1696132800); // 10/01/2023 00:00:00-0400
        Date endDate2 = new Date(1698724800); // 10/01/2023 00:00:00-0400

        BillingCycle billingCycle1 = new BillingCycle("1", "5555555555", startDate1, endDate1, "testUserId1");
        BillingCycle billingCycle2 = new BillingCycle("2", "4444444444", startDate1, endDate1, "testUserId2");
        BillingCycle billingCycle3 = new BillingCycle("3", "1111111111", startDate1, endDate1, "testUserId1");
        BillingCycle billingCycle4 = new BillingCycle("4", "5555555555", startDate2, endDate2, "testUserId1");

        return List.of(billingCycle1, billingCycle2, billingCycle3, billingCycle4);
    }

    @Test
    @DisplayName("findCurrentBillingCycle returns the current active billing cycle if the date falls within the cycle for a given userId and mdn")
    void testFindCurrentBillingCycle() {
        // Arrange
        Date today = new Date(1729137600); // 10/17/2024 00:00:00-0400

        // Act
        List<BillingCycle> currentBillingCyles = billingCycleRepository.findCurrentBillingCycle("testUserId1", "5555555555", today);
        BillingCycle currentBillingCyle = currentBillingCyles.get(0);

        // Assert
        assertThat(currentBillingCyles.size()).isEqualTo(1);
        assertThat(today).isAfterOrEqualTo(currentBillingCyle.getStartDate()).isBeforeOrEqualTo(currentBillingCyle.getEndDate());
    }

    @Test
    @DisplayName("findCurrentBillingCycle returns all billing cycle documents for a given userId and mdn")
    void testFindBillingCycleHistory() {
        // Arrange

        // Act
        List<BillingCycle> currentBillingCyles = billingCycleRepository.findBillingCycleHistory("testUserId1", "5555555555");

        // Assert
        assertThat(currentBillingCyles.size()).isEqualTo(2);
    }
}