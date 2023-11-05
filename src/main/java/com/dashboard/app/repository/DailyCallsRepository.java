package com.dashboard.app.repository;

import com.dashboard.app.domain.DailyCalls;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DailyCalls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyCallsRepository extends JpaRepository<DailyCalls, Long>, JpaSpecificationExecutor<DailyCalls> {}
