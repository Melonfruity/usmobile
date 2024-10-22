package com.usmobile.assessment.cycle_usage_service.dto.v1;

import com.usmobile.assessment.cycle_usage_service.models.BillingCycle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BillingCycleDTOMapper {
    BillingCycleDTOMapper INSTANCE = Mappers.getMapper(BillingCycleDTOMapper.class);

    BillingCycleDTO toDTO(BillingCycle billingCycle);
    BillingCycle toEntity(BillingCycleDTO billingCycleDTO);
}