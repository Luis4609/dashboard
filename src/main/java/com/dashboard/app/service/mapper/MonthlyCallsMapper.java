package com.dashboard.app.service.mapper;

import com.dashboard.app.domain.DailyCalls;
import com.dashboard.app.service.dto.DailyCallsDTO;
import com.dashboard.app.service.dto.MonthlyCallsDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link DailyCalls} and its DTO {@link DailyCallsDTO}.
 */
@Mapper(componentModel = "spring")
public interface MonthlyCallsMapper extends EntityMapper<MonthlyCallsDTO, DailyCalls> {}
