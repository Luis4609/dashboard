package com.dashboard.app.service.mapper;

import com.dashboard.app.domain.DailyChats;
import com.dashboard.app.service.dto.DailyChatsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyChats} and its DTO {@link DailyChatsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DailyChatsMapper extends EntityMapper<DailyChatsDTO, DailyChats> {}
