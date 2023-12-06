package com.dashboard.app.service.dto;

public class DailyCallsMetricsDTO {

    private int totalReceivedCall;
    private int totalAttendedCalls;
    private int totalLostCalls;

    public DailyCallsMetricsDTO() {}

    public int getTotalReceivedCall() {
        return totalReceivedCall;
    }

    public void setTotalReceivedCall(int totalReceivedCall) {
        this.totalReceivedCall = totalReceivedCall;
    }

    public int getTotalAttendedCalls() {
        return totalAttendedCalls;
    }

    public void setTotalAttendedCalls(int totalAttendedCalls) {
        this.totalAttendedCalls = totalAttendedCalls;
    }

    public int getTotalLostCalls() {
        return totalLostCalls;
    }

    public void setTotalLostCalls(int totalLostCalls) {
        this.totalLostCalls = totalLostCalls;
    }

    @Override
    public String toString() {
        return (
            "DailyCallsMetrics{" +
            "totalReceivedCall=" +
            totalReceivedCall +
            ", totalAttendedCalls=" +
            totalAttendedCalls +
            ", totalLostCalls=" +
            totalLostCalls +
            '}'
        );
    }
}
