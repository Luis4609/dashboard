package com.dashboard.app.web.rest;

import com.dashboard.app.service.MainDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Main dashboard controller.
 */
@RestController
@RequestMapping("/api")
public class MainDashboardController {

    private final MainDashboardService mainDashboardService;

    /**
     * Instantiates a new Main dashboard controller.
     *
     * @param mainDashboardService the main dashboard service
     */
    public MainDashboardController(MainDashboardService mainDashboardService) {
        this.mainDashboardService = mainDashboardService;
    }

    /**
     * Gets main calls metrics.
     *
     * @return the main calls metrics
     */
    @GetMapping("/main/calls-metrics")
    public ResponseEntity<Object> getMainCallsMetrics() {
        return ResponseEntity.ok().body(mainDashboardService.getMainCallsMetrics());
    }

    /**
     * Gets calls time metrics.
     *
     * @return the calls time metrics
     */
    @GetMapping("/main/calls-time-metrics")
    public ResponseEntity<Object> getCallsTimeMetrics() {
        return ResponseEntity.ok().body(mainDashboardService.getCallsTimeMetrics());
    }

    /**
     * Gets main chats metrics.
     *
     * @return the main chats metrics
     */
    @GetMapping("/main/chats-metrics")
    public ResponseEntity<Object> getMainChatsMetrics() {
        return ResponseEntity.ok().body(mainDashboardService.getMainChatsMetrics());
    }
}
