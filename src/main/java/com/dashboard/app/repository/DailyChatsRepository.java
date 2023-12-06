package com.dashboard.app.repository;

import com.dashboard.app.domain.DailyChats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DailyChats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyChatsRepository extends JpaRepository<DailyChats, Long>, JpaSpecificationExecutor<DailyChats> {
    @Query(
        value = "SELECT SUM(total_daily_attended_chats), SUM(total_daily_received_chats)," +
        "SUM(total_daily_missed_chats), SUM(total_daily_conversation_chats_time_in_min)\n" +
        "FROM dashboard.daily_chats\n" +
        "WHERE day between ? AND ?",
        nativeQuery = true
    )
    Object getMainChatsMetrics();
}
