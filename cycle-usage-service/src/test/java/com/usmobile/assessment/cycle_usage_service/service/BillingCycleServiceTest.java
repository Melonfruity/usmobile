package com.usmobile.assessment.cycle_usage_service.service;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import com.usmobile.assessment.cycle_usage_service.repository.BillingCycleRepository;
import com.usmobile.assessment.cycle_usage_service.repository.DailyUsageRepository;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleHistoryResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleUsageResponse;
import org.apache.commons.lang3.time.DateUtils;
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
class BillingCycleServiceTest {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    private final DailyUsageRepository dailyUsageRepository;
    private final BillingCycleRepository billingCycleRepository;

    private final BillingCycleService billingCycleService;

    @Autowired
    public BillingCycleServiceTest(DailyUsageRepository dailyUsageRepository, BillingCycleRepository billingCycleRepository, BillingCycleService billingCycleService) {
        this.billingCycleRepository = billingCycleRepository;
        this.dailyUsageRepository = dailyUsageRepository;
        this.billingCycleService = billingCycleService;
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @BeforeEach
    public void setUp() {
        // Initialize Mocks
        billingCycleRepository.saveAll(createMockBillingCycles());
        dailyUsageRepository.saveAll(createMockDailyUsages());
    }

    private List<BillingCycle> createMockBillingCycles() {
        Date currentDate = new Date();

        Date date15DaysBefore = DateUtils.addDays(currentDate, -15);
        Date date15DaysAfter = DateUtils.addDays(currentDate, 15);
        Date date45DaysBefore = DateUtils.addDays(currentDate, -45);

        BillingCycle billingCycle1 = new BillingCycle("1", "5555555555", date15DaysBefore, date15DaysAfter, "testUserId1", date15DaysBefore, date15DaysBefore);
        BillingCycle billingCycle2 = new BillingCycle("2", "4444444444", date15DaysBefore, date15DaysAfter, "testUserId2", date15DaysBefore, date15DaysBefore);
        BillingCycle billingCycle3 = new BillingCycle("3", "1111111111", date15DaysBefore, date15DaysAfter, "testUserId1", date15DaysBefore, date15DaysBefore);
        BillingCycle billingCycle4 = new BillingCycle("4", "5555555555", date45DaysBefore, date15DaysBefore, "testUserId1", date45DaysBefore, date45DaysBefore);

        return List.of(billingCycle1, billingCycle2, billingCycle3, billingCycle4);
    }

    private List<DailyUsage> createMockDailyUsages() {
        Date currentDate = new Date();

        Date date30DaysBefore = DateUtils.addDays(currentDate, -30);
        Date date1YearBefore = DateUtils.addYears(currentDate, -1);
        Date date10DaysBefore = DateUtils.addDays(currentDate, -10);
        Date date15DaysAfter = DateUtils.addDays(currentDate, 15);

        DailyUsage dailyUsage1 = new DailyUsage("1", "5555555555", "testUserId1", date30DaysBefore, 10.1, date30DaysBefore, date30DaysBefore); // Outside Cycle
        DailyUsage dailyUsage2 = new DailyUsage("2", "5555555555", "testUserId1", date1YearBefore, 20.2, date1YearBefore, date1YearBefore); // Outside Cycle
        DailyUsage dailyUsage3 = new DailyUsage("3", "5555555555", "testUserId1", date10DaysBefore, 30.3, date10DaysBefore, date10DaysBefore); // Within Cycle
        DailyUsage dailyUsage4 = new DailyUsage("4", "5555555555", "testUserId1", date15DaysAfter, 40.4, date15DaysAfter, date15DaysAfter); // Within Cycle
        DailyUsage dailyUsage5 = new DailyUsage("5", "1111111111", "testUserId1", date10DaysBefore, 50.5, date10DaysBefore, date10DaysBefore); // Within Cycle Diff MDN
        DailyUsage dailyUsage6 = new DailyUsage("6", "4444444444", "testUserId2", date10DaysBefore, 60.6, date10DaysBefore, date10DaysBefore); // Within Cycle Diff User

        return List.of(dailyUsage1, dailyUsage2, dailyUsage3, dailyUsage4, dailyUsage5, dailyUsage6);
    }

    @Test
    @DisplayName("getCycleUsage should return the daily usage for the current cycle")
    void testGetDailyUsageForCurrentCycle() throws Exception {
        // Arrange
        // Act
        BillingCycleUsageResponse billingCycleDailyUsages = billingCycleService.getDailyUsageForCurrentCycle("testUserId1", "1111111111");

        // Assert
        // Spy on getDailyUsageForCurrentCycle is called with the correct input
        // Spy on findCurrentBillingCycle is called with the correct input
        // Spy on findUsageWithinCycle is called with the correct input
        assertThat(billingCycleDailyUsages.getDailyUsages().size()).isEqualTo(1);
        assertThat(billingCycleDailyUsages.getDailyUsages().get(0).getUsageInMb()).isEqualTo(50.5);
    }

    @Test
    @DisplayName("getCycleUsage should return the daily usage for the current cycle")
    void testGetCycleHistory() {
        // Arrange

        // Act
        BillingCycleHistoryResponse billingCycleHistory = billingCycleService.getCycleHistory("testUserId1", "5555555555");

        // Assert
        // Spy on getCycleHistory is called with the correct input
        assertThat(billingCycleHistory.getBillingCycleList().size()).isEqualTo(2);
    }
}