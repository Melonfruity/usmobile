package com.usmobile.assessment.cycle_usage_service.dto.v1;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-22T00:45:27-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class BillingCycleDTOMapperImpl implements BillingCycleDTOMapper {

    @Override
    public BillingCycleDTO toDTO(BillingCycle billingCycle) {
        if ( billingCycle == null ) {
            return null;
        }

        BillingCycleDTO billingCycleDTO = new BillingCycleDTO();

        billingCycleDTO.setUserId( billingCycle.getUserId() );
        billingCycleDTO.setMdn( billingCycle.getMdn() );
        billingCycleDTO.setStartDate( billingCycle.getStartDate() );
        billingCycleDTO.setEndDate( billingCycle.getEndDate() );

        return billingCycleDTO;
    }

    @Override
    public BillingCycle toEntity(BillingCycleDTO billingCycleDTO) {
        if ( billingCycleDTO == null ) {
            return null;
        }

        BillingCycle billingCycle = new BillingCycle();

        billingCycle.setMdn( billingCycleDTO.getMdn() );
        billingCycle.setStartDate( billingCycleDTO.getStartDate() );
        billingCycle.setEndDate( billingCycleDTO.getEndDate() );
        billingCycle.setUserId( billingCycleDTO.getUserId() );

        return billingCycle;
    }
}
