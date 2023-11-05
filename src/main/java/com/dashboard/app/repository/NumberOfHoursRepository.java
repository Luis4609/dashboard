package com.dashboard.app.repository;

import com.dashboard.app.domain.NumberOfHours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NumberOfHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumberOfHoursRepository extends JpaRepository<NumberOfHours, Long>, JpaSpecificationExecutor<NumberOfHours> {}
