package com.dashboard.app.repository;

import com.dashboard.app.domain.DailyChats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DailyChats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyChatsRepository extends JpaRepository<DailyChats, Long>, JpaSpecificationExecutor<DailyChats> {}
