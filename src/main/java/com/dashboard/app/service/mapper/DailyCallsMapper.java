package com.dashboard.app.service.mapper;

import com.dashboard.app.domain.DailyCalls;
import com.dashboard.app.service.dto.DailyCallsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyCalls} and its DTO {@link DailyCallsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DailyCallsMapper extends EntityMapper<DailyCallsDTO, DailyCalls> {}
