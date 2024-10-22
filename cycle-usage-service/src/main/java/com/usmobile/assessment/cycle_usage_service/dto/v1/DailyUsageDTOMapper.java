package com.usmobile.assessment.cycle_usage_service.dto.v1;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DailyUsageDTOMapper {
    DailyUsageDTOMapper INSTANCE = Mappers.getMapper(DailyUsageDTOMapper.class);

    DailyUsageDTO toDTO(DailyUsage dailyUsage);
    DailyUsage toEntity(DailyUsageDTO dailyUsageDTO);
}