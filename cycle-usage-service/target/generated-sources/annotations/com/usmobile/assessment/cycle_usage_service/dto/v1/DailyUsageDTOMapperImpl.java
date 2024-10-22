package com.usmobile.assessment.cycle_usage_service.dto.v1;

import com.usmobile.assessment.cycle_usage_service.models.DailyUsage;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-22T00:51:33-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class DailyUsageDTOMapperImpl implements DailyUsageDTOMapper {

    @Override
    public DailyUsageDTO toDTO(DailyUsage dailyUsage) {
        if ( dailyUsage == null ) {
            return null;
        }

        DailyUsageDTO dailyUsageDTO = new DailyUsageDTO();

        dailyUsageDTO.setMdn( dailyUsage.getMdn() );
        dailyUsageDTO.setUserId( dailyUsage.getUserId() );
        dailyUsageDTO.setUsageDate( dailyUsage.getUsageDate() );
        dailyUsageDTO.setUsageInMb( dailyUsage.getUsageInMb() );

        return dailyUsageDTO;
    }

    @Override
    public DailyUsage toEntity(DailyUsageDTO dailyUsageDTO) {
        if ( dailyUsageDTO == null ) {
            return null;
        }

        DailyUsage dailyUsage = new DailyUsage();

        dailyUsage.setMdn( dailyUsageDTO.getMdn() );
        dailyUsage.setUserId( dailyUsageDTO.getUserId() );
        dailyUsage.setUsageDate( dailyUsageDTO.getUsageDate() );
        dailyUsage.setUsageInMb( dailyUsageDTO.getUsageInMb() );

        return dailyUsage;
    }
}
