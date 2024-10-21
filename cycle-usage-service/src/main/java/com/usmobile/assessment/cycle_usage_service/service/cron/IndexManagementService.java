package com.usmobile.assessment.cycle_usage_service.service.cron;

import com.usmobile.assessment.cycle_usage_service.service.DailyUsageService;
import com.usmobile.assessment.cycle_usage_service.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class IndexManagementService {
    private DailyUsageService dailyUsageService;

    @Autowired
    public IndexManagementService(DailyUsageService dailyUsageService) {
        this.dailyUsageService = dailyUsageService;
    }

    // Execute every 15 minutes
    @Scheduled(cron = "0 0/15 * * * ?")
    public void refreshDailyUsageIndexes() {
        LoggerUtil.logInfo("Refreshing Daily Usage Indexes"); // Did I spell this wrong?

        // Should not be needed since Indexes are automatically managed by MongoDB...
        // Insertion: When you insert new documents, MongoDB automatically updates the indexes. If the indexes are properly defined on the fields being used in queries, they will reflect the new entries without needing manual intervention.
        // Deletion: Similarly, when documents are deleted, MongoDB updates the indexes to remove references to those documents.
        // Updating: If a document is updated and the indexed fields change, MongoDB will update the relevant indexes accordingly.
    }
}