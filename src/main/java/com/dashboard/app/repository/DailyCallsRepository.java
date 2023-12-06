package com.dashboard.app.repository;

import com.dashboard.app.domain.DailyCalls;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DailyCalls entity.
 */
@Repository
public interface DailyCallsRepository extends JpaRepository<DailyCalls, Long>, JpaSpecificationExecutor<DailyCalls> {
    Optional<DailyCalls> findByDay(LocalDate localDate);

    @Query(
        value = """
        SELECT id, DATE_FORMAT(day, '%Y-%m') AS day, SUM(dc.total_daily_received_calls), SUM(dc.total_daily_attended_calls),
        SUM(dc.total_daily_missed_calls), SUM(dc.total_daily_received_calls_external_agent),
        SUM(dc.total_daily_attended_calls_external_agent), SUM(dc.total_daily_received_calls_internal_agent),
        SUM(dc.total_daily_attended_calls_internal_agent), SUM(dc.total_daily_calls_time_in_min),
        SUM(dc.total_daily_calls_time_external_agent_in_min), SUM(dc.total_daily_calls_time_internal_agent_in_min),
        AVG(dc.avg_daily_operation_time_external_agent_in_min), AVG(dc.avg_daily_operation_time_internal_agent_in_min)
        FROM dashboard.daily_calls dc
        WHERE dc.day between ?1 AND ?2
        GROUP BY month(dc.day), year(dc.day)""",
        nativeQuery = true
    )
    List<DailyCalls> getMonthlyTotalByDateRange(Date start, Date end);

    @Query(
        value = """
        SELECT SUM(total_daily_received_calls) AS TotalReceived,
        SUM(total_daily_attended_calls) AS TotalAttended,
        SUM(total_daily_missed_calls) AS TotalMissed,
        FROM dashboard.daily_calls""",
        nativeQuery = true
    )
    Object[] getMainCallsMetrics();

    @Query(
        value = """
        SELECT AVG(total_daily_calls_time_in_min), AVG(total_daily_calls_time_external_agent_in_min),
        AVG(total_daily_calls_time_internal_agent_in_min), AVG(avg_daily_operation_time_external_agent_in_min),
        AVG(avg_daily_operation_time_internal_agent_in_min)
        FROM dashboard.daily_calls""",
        nativeQuery = true
    )
    Object getAvgTimeCallsMetrics();

    @Query(
        value = """
        SELECT SUM(total_daily_received_calls), SUM(total_daily_attended_calls), SUM(total_daily_missed_calls)
        FROM dashboard.daily_calls
        WHERE daily_calls.day between DATE(?1) AND CURRENT_DATE()""",
        nativeQuery = true
    )
    Object getDailyCallMetricsByDate(Date start);
}
