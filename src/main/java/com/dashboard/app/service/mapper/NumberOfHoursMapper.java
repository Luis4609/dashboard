package com.dashboard.app.service.mapper;

import com.dashboard.app.domain.NumberOfHours;
import com.dashboard.app.service.dto.NumberOfHoursDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NumberOfHours} and its DTO {@link NumberOfHoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface NumberOfHoursMapper extends EntityMapper<NumberOfHoursDTO, NumberOfHours> {}
