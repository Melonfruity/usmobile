package com.usmobile.assessment.cycle_usage_service.service;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import com.usmobile.assessment.cycle_usage_service.repository.BillingCycleRepository;
import com.usmobile.assessment.cycle_usage_service.repository.DailyUsageRepository;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleHistoryResponse;
import com.usmobile.assessment.cycle_usage_service.response.v1.BillingCycleUsageResponse;
import com.usmobile.assessment.cycle_usage_service.service.util.DateProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class BillingCycleServiceTest {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    private final DailyUsageRepository dailyUsageRepository;
    private final BillingCycleRepository billingCycleRepository;

    @InjectMocks
    private BillingCycleService billingCycleService;

    @MockBean
    private DateProviderService dateProviderService;

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

    private List<DailyUsage> createMockDailyUsages() {
        Date date1 = new Date(1726394400); // 09/15/2024 06:00:00-0400
        Date date2 = new Date(1697364000); // 10/15/2023 06:00:00-0400
        Date date3 = new Date(1729000800); // 10/15/2024 10:00:00-0400
        Date date4 = new Date(1729418400); // 10/20/2024 06:00:00-0400

        DailyUsage dailyUsage1 = new DailyUsage("1", "5555555555", "testUserId1", date1, 10.1); // Outside Cycle
        DailyUsage dailyUsage2 = new DailyUsage("2", "5555555555", "testUserId1", date2, 20.2); // Outside Cycle
        DailyUsage dailyUsage3 = new DailyUsage("3", "5555555555", "testUserId1", date3, 30.3); // Within Cycle
        DailyUsage dailyUsage4 = new DailyUsage("4", "5555555555", "testUserId1", date4, 40.4); // Within Cycle
        DailyUsage dailyUsage5 = new DailyUsage("5", "1111111111", "testUserId1", date4, 50.5); // Within Cycle Diff MDN
        DailyUsage dailyUsage6 = new DailyUsage("6", "4444444444", "testUserId2", date4, 60.6); // Within Cycle Diff User

        return List.of(dailyUsage1, dailyUsage2, dailyUsage3, dailyUsage4, dailyUsage5, dailyUsage6);
    }

    @Test
    @DisplayName("getCycleUsage should return the daily usage for the current cycle")
    void testGetDailyUsageForCurrentCycle() {
        // Arrange
        Date today = new Date(1729137600); // 10/17/2024 00:00:00-0400

        // Mock DateProviderService to return the fixed date
        when(dateProviderService.getCurrentDate()).thenReturn(today);

        // Act
        BillingCycleUsageResponse billingCycleDailyUsages = billingCycleService.getDailyUsageForCurrentCycle("testUserId1", "1111111111");

        // Assert
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
        assertThat(billingCycleHistory.getBillingCycleList().size()).isEqualTo(2);
    }
}