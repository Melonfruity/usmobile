package com.usmobile.assessment.cycle_usage_service.repository;

import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
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
class DailyUsageRepositoryTest {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    private final DailyUsageRepository dailyUsageRepository;

    @Autowired
    public DailyUsageRepositoryTest(DailyUsageRepository dailyUsageRepository) {
        this.dailyUsageRepository = dailyUsageRepository;
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @BeforeEach
    public void setUp() {
        dailyUsageRepository.saveAll(createMockDailyUsages());
    }

    private List<DailyUsage> createMockDailyUsages() {
        Date currentDate = new java.util.Date();

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
    @DisplayName("findUsageWithinCycle should return the daily usages within start and end date for testUserId1 with mdn")
    public void testFindUsageWithinCycle() {
        Date currentDate = new Date();
        Date startDate = DateUtils.addDays(currentDate, -15); // 10/01/2024 00:00:00-0400
        Date endDate = DateUtils.addDays(currentDate, 15); // 10/31/2024 00:00:00-0400

        List<DailyUsage> dailyUsages = dailyUsageRepository.findUsageWithinCycle("testUserId1", "5555555555", startDate, endDate);

        assertThat(dailyUsages.size()).isEqualTo(2);
        assertThat(dailyUsages.get(0).getUsageInMb()).isEqualTo(30.3);
        assertThat(dailyUsages.get(1).getUsageInMb()).isEqualTo(40.4);
    }
}