//package com.usmobile.assessment.cycle_usage_service.repository;
//
//import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@DataMongoTest
//public class DailyUsageRepositoryTest {
//
//    @Autowired
//    private DailyUsageRepository dailyUsageRepository;
//
//    @BeforeEach
//    void setUp() {
//        dailyUsageRepository.deleteAll();
//        // Add test data
//        DailyUsage usage1 = new DailyUsage("1", "1111111111", "6714362dff8b140a6d5df592", new Date("2024-10-20T04:00:00.000+0000"), 11.0);
//        DailyUsage usage2 = new DailyUsage("2", "2222222222", "6714362dff8b140a6d5df592", new Date("2022-10-20T04:00:00.000+0000"), 1.0);
//        dailyUsageRepository.save(usage1);
//        dailyUsageRepository.save(usage2);
//    }
//
//    @Test
//    void findUsageWithinCycle() {
//        Date startDate = new Date("2024-10-01T04:00:00.000+0000");
//        Date endDate = new Date("2024-10-30T04:00:00.000+0000");
//        List<DailyUsage> results = dailyUsageRepository.findUsageWithinCycle("6714362dff8b140a6d5df592", "1111111111", startDate, endDate);
//        assertThat(results).isNotEmpty();
//        assertThat(results.size()).isEqualTo(1);
//        assertThat(results.get(0).getMdn()).isEqualTo("1111111111");
//    }
//}