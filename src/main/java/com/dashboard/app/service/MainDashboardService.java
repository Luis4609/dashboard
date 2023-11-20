package com.dashboard.app.service;

import com.dashboard.app.repository.DailyCallsRepository;
import org.springframework.stereotype.Service;

/**
 * The type Main dashboard service.
 */
@Service
public class MainDashboardService {

    private final DailyCallsRepository dailyCallsRepository;

    /**
     * Instantiates a new Main dashboard service.
     *
     * @param dailyCallsRepository the daily calls repository
     */
    public MainDashboardService(DailyCallsRepository dailyCallsRepository) {
        this.dailyCallsRepository = dailyCallsRepository;
    }

    /**
     * Gets main calls metrics.
     *
     * @return the main calls metrics
     */
    public Object getMainCallsMetrics() {
        return dailyCallsRepository.getMainCallsMetrics();
    }

    /**
     * Gets calls time metrics.
     *
     * @return the calls time metrics
     */
    public Object getCallsTimeMetrics() {
        return dailyCallsRepository.getAvgTimeCallsMetrics();
    }

    public Object getMainChatsMetrics() {
        return dailyCallsRepository.getMainChatsMetrics();
    }
}
