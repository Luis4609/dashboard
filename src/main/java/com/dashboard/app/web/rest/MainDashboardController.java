package com.dashboard.app.web.rest;

import com.dashboard.app.service.MainDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainDashboardController {

    private final MainDashboardService mainDashboardService;

    public MainDashboardController(MainDashboardService mainDashboardService) {
        this.mainDashboardService = mainDashboardService;
    }

    @GetMapping("/main/calls-metrics")
    public ResponseEntity<Object> getMainCallsMetrics() {
        return ResponseEntity.ok().body(mainDashboardService.getMainCallsMetrics());
    }
}
