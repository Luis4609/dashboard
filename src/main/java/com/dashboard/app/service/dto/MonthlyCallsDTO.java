package com.dashboard.app.service.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class MonthlyCallsDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate day;

    private Integer totalMonthlyReceivedCalls;

    @NotNull
    private Integer totalMonthlyAttendedCalls;

    @NotNull
    private Integer totalMonthlyMissedCalls;

    @NotNull
    private Integer totalMonthlyReceivedCallsExternalAgent;

    @NotNull
    private Integer totalMonthlyAttendedCallsExternalAgent;

    private Integer totalMonthlyReceivedCallsInternalAgent;

    @NotNull
    private Integer totalMonthlyAttendedCallsInternalAgent;

    @NotNull
    private Integer totalMonthlyCallsTimeInMin;

    @NotNull
    private Integer totalMonthlyCallsTimeExternalAgentInMin;

    @NotNull
    private Integer totalMonthlyCallsTimeInternalAgentInMin;

    @NotNull
    private Float avgDailyOperationTimeExternalAgentInMin;

    private Float avgDailyOperationTimeInternalAgentInMin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getTotalMonthlyReceivedCalls() {
        return totalMonthlyReceivedCalls;
    }

    public void setTotalMonthlyReceivedCalls(Integer totalMonthlyReceivedCalls) {
        this.totalMonthlyReceivedCalls = totalMonthlyReceivedCalls;
    }

    public Integer getTotalMonthlyAttendedCalls() {
        return totalMonthlyAttendedCalls;
    }

    public void setTotalMonthlyAttendedCalls(Integer totalMonthlyAttendedCalls) {
        this.totalMonthlyAttendedCalls = totalMonthlyAttendedCalls;
    }

    public Integer getTotalMonthlyMissedCalls() {
        return totalMonthlyMissedCalls;
    }

    public void setTotalMonthlyMissedCalls(Integer totalMonthlyMissedCalls) {
        this.totalMonthlyMissedCalls = totalMonthlyMissedCalls;
    }

    public Integer getTotalMonthlyReceivedCallsExternalAgent() {
        return totalMonthlyReceivedCallsExternalAgent;
    }

    public void setTotalMonthlyReceivedCallsExternalAgent(Integer totalMonthlyReceivedCallsExternalAgent) {
        this.totalMonthlyReceivedCallsExternalAgent = totalMonthlyReceivedCallsExternalAgent;
    }

    public Integer getTotalMonthlyAttendedCallsExternalAgent() {
        return totalMonthlyAttendedCallsExternalAgent;
    }

    public void setTotalMonthlyAttendedCallsExternalAgent(Integer totalMonthlyAttendedCallsExternalAgent) {
        this.totalMonthlyAttendedCallsExternalAgent = totalMonthlyAttendedCallsExternalAgent;
    }

    public Integer getTotalMonthlyReceivedCallsInternalAgent() {
        return totalMonthlyReceivedCallsInternalAgent;
    }

    public void setTotalMonthlyReceivedCallsInternalAgent(Integer totalMonthlyReceivedCallsInternalAgent) {
        this.totalMonthlyReceivedCallsInternalAgent = totalMonthlyReceivedCallsInternalAgent;
    }

    public Integer getTotalMonthlyAttendedCallsInternalAgent() {
        return totalMonthlyAttendedCallsInternalAgent;
    }

    public void setTotalMonthlyAttendedCallsInternalAgent(Integer totalMonthlyAttendedCallsInternalAgent) {
        this.totalMonthlyAttendedCallsInternalAgent = totalMonthlyAttendedCallsInternalAgent;
    }

    public Integer getTotalMonthlyCallsTimeInMin() {
        return totalMonthlyCallsTimeInMin;
    }

    public void setTotalMonthlyCallsTimeInMin(Integer totalMonthlyCallsTimeInMin) {
        this.totalMonthlyCallsTimeInMin = totalMonthlyCallsTimeInMin;
    }

    public Integer getTotalMonthlyCallsTimeExternalAgentInMin() {
        return totalMonthlyCallsTimeExternalAgentInMin;
    }

    public void setTotalMonthlyCallsTimeExternalAgentInMin(Integer totalMonthlyCallsTimeExternalAgentInMin) {
        this.totalMonthlyCallsTimeExternalAgentInMin = totalMonthlyCallsTimeExternalAgentInMin;
    }

    public Integer getTotalMonthlyCallsTimeInternalAgentInMin() {
        return totalMonthlyCallsTimeInternalAgentInMin;
    }

    public void setTotalMonthlyCallsTimeInternalAgentInMin(Integer totalMonthlyCallsTimeInternalAgentInMin) {
        this.totalMonthlyCallsTimeInternalAgentInMin = totalMonthlyCallsTimeInternalAgentInMin;
    }

    public Float getAvgDailyOperationTimeExternalAgentInMin() {
        return avgDailyOperationTimeExternalAgentInMin;
    }

    public void setAvgDailyOperationTimeExternalAgentInMin(Float avgDailyOperationTimeExternalAgentInMin) {
        this.avgDailyOperationTimeExternalAgentInMin = avgDailyOperationTimeExternalAgentInMin;
    }

    public Float getAvgDailyOperationTimeInternalAgentInMin() {
        return avgDailyOperationTimeInternalAgentInMin;
    }

    public void setAvgDailyOperationTimeInternalAgentInMin(Float avgDailyOperationTimeInternalAgentInMin) {
        this.avgDailyOperationTimeInternalAgentInMin = avgDailyOperationTimeInternalAgentInMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyCallsDTO)) {
            return false;
        }

        MonthlyCallsDTO dailyCallsDTO = (MonthlyCallsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dailyCallsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyCallsDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", totalDailyReceivedCalls=" + getTotalMonthlyReceivedCalls() +
            ", totalDailyAttendedCalls=" + getTotalMonthlyAttendedCalls() +
            ", totalDailyMissedCalls=" + getTotalMonthlyMissedCalls() +
            ", totalDailyReceivedCallsExternalAgent=" + getTotalMonthlyReceivedCallsExternalAgent() +
            ", totalDailyAttendedCallsExternalAgent=" + getTotalMonthlyAttendedCallsExternalAgent() +
            ", totalDailyReceivedCallsInternalAgent=" + getTotalMonthlyReceivedCallsInternalAgent() +
            ", totalDailyAttendedCallsInternalAgent=" + getTotalMonthlyAttendedCallsInternalAgent() +
            ", totalDailyCallsTimeInMin=" + getTotalMonthlyCallsTimeInMin() +
            ", totalDailyCallsTimeExternalAgentInMin=" + getTotalMonthlyCallsTimeExternalAgentInMin() +
            ", totalDailyCallsTimeInternalAgentInMin=" + getTotalMonthlyCallsTimeInternalAgentInMin() +
            ", avgDailyOperationTimeExternalAgentInMin=" + getAvgDailyOperationTimeExternalAgentInMin() +
            ", avgDailyOperationTimeInternalAgentInMin=" + getAvgDailyOperationTimeInternalAgentInMin() +
            "}";
    }
}
