package com.dashboard.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dashboard.app.domain.NumberOfHours} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NumberOfHoursDTO implements Serializable {

    private Long id;

    @NotNull
    private String month;

    @NotNull
    private Double externalAgentDailyHoursAvg;

    private Double dailyHourAvg;

    private Integer avgHoursToAnswerCalls;

    private Double totalHoursToAnswerCalls;

    private Integer totalReceivedCalls;

    private Integer totalAttendedCalls;

    private Double attendedCallsPercentage;

    private Double avgDailyAttendedCalls;

    private Double avgDailyAttendedCallsByExternal;

    private Double avgDailyAttendedCallsByExternalByDay;

    private Double avgDailyAttendedCallsByInternal;

    private Integer totalReceivedChats;

    private Integer totalAttendedChats;

    private Integer totalReceivedMails;

    private Integer totalAttendedMails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getExternalAgentDailyHoursAvg() {
        return externalAgentDailyHoursAvg;
    }

    public void setExternalAgentDailyHoursAvg(Double externalAgentDailyHoursAvg) {
        this.externalAgentDailyHoursAvg = externalAgentDailyHoursAvg;
    }

    public Double getDailyHourAvg() {
        return dailyHourAvg;
    }

    public void setDailyHourAvg(Double dailyHourAvg) {
        this.dailyHourAvg = dailyHourAvg;
    }

    public Integer getAvgHoursToAnswerCalls() {
        return avgHoursToAnswerCalls;
    }

    public void setAvgHoursToAnswerCalls(Integer avgHoursToAnswerCalls) {
        this.avgHoursToAnswerCalls = avgHoursToAnswerCalls;
    }

    public Double getTotalHoursToAnswerCalls() {
        return totalHoursToAnswerCalls;
    }

    public void setTotalHoursToAnswerCalls(Double totalHoursToAnswerCalls) {
        this.totalHoursToAnswerCalls = totalHoursToAnswerCalls;
    }

    public Integer getTotalReceivedCalls() {
        return totalReceivedCalls;
    }

    public void setTotalReceivedCalls(Integer totalReceivedCalls) {
        this.totalReceivedCalls = totalReceivedCalls;
    }

    public Integer getTotalAttendedCalls() {
        return totalAttendedCalls;
    }

    public void setTotalAttendedCalls(Integer totalAttendedCalls) {
        this.totalAttendedCalls = totalAttendedCalls;
    }

    public Double getAttendedCallsPercentage() {
        return attendedCallsPercentage;
    }

    public void setAttendedCallsPercentage(Double attendedCallsPercentage) {
        this.attendedCallsPercentage = attendedCallsPercentage;
    }

    public Double getAvgDailyAttendedCalls() {
        return avgDailyAttendedCalls;
    }

    public void setAvgDailyAttendedCalls(Double avgDailyAttendedCalls) {
        this.avgDailyAttendedCalls = avgDailyAttendedCalls;
    }

    public Double getAvgDailyAttendedCallsByExternal() {
        return avgDailyAttendedCallsByExternal;
    }

    public void setAvgDailyAttendedCallsByExternal(Double avgDailyAttendedCallsByExternal) {
        this.avgDailyAttendedCallsByExternal = avgDailyAttendedCallsByExternal;
    }

    public Double getAvgDailyAttendedCallsByExternalByDay() {
        return avgDailyAttendedCallsByExternalByDay;
    }

    public void setAvgDailyAttendedCallsByExternalByDay(Double avgDailyAttendedCallsByExternalByDay) {
        this.avgDailyAttendedCallsByExternalByDay = avgDailyAttendedCallsByExternalByDay;
    }

    public Double getAvgDailyAttendedCallsByInternal() {
        return avgDailyAttendedCallsByInternal;
    }

    public void setAvgDailyAttendedCallsByInternal(Double avgDailyAttendedCallsByInternal) {
        this.avgDailyAttendedCallsByInternal = avgDailyAttendedCallsByInternal;
    }

    public Integer getTotalReceivedChats() {
        return totalReceivedChats;
    }

    public void setTotalReceivedChats(Integer totalReceivedChats) {
        this.totalReceivedChats = totalReceivedChats;
    }

    public Integer getTotalAttendedChats() {
        return totalAttendedChats;
    }

    public void setTotalAttendedChats(Integer totalAttendedChats) {
        this.totalAttendedChats = totalAttendedChats;
    }

    public Integer getTotalReceivedMails() {
        return totalReceivedMails;
    }

    public void setTotalReceivedMails(Integer totalReceivedMails) {
        this.totalReceivedMails = totalReceivedMails;
    }

    public Integer getTotalAttendedMails() {
        return totalAttendedMails;
    }

    public void setTotalAttendedMails(Integer totalAttendedMails) {
        this.totalAttendedMails = totalAttendedMails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberOfHoursDTO)) {
            return false;
        }

        NumberOfHoursDTO numberOfHoursDTO = (NumberOfHoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, numberOfHoursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumberOfHoursDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", externalAgentDailyHoursAvg=" + getExternalAgentDailyHoursAvg() +
            ", dailyHourAvg=" + getDailyHourAvg() +
            ", avgHoursToAnswerCalls=" + getAvgHoursToAnswerCalls() +
            ", totalHoursToAnswerCalls=" + getTotalHoursToAnswerCalls() +
            ", totalReceivedCalls=" + getTotalReceivedCalls() +
            ", totalAttendedCalls=" + getTotalAttendedCalls() +
            ", attendedCallsPercentage=" + getAttendedCallsPercentage() +
            ", avgDailyAttendedCalls=" + getAvgDailyAttendedCalls() +
            ", avgDailyAttendedCallsByExternal=" + getAvgDailyAttendedCallsByExternal() +
            ", avgDailyAttendedCallsByExternalByDay=" + getAvgDailyAttendedCallsByExternalByDay() +
            ", avgDailyAttendedCallsByInternal=" + getAvgDailyAttendedCallsByInternal() +
            ", totalReceivedChats=" + getTotalReceivedChats() +
            ", totalAttendedChats=" + getTotalAttendedChats() +
            ", totalReceivedMails=" + getTotalReceivedMails() +
            ", totalAttendedMails=" + getTotalAttendedMails() +
            "}";
    }
}
