package com.dashboard.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NumberOfHours.
 */
@Entity
@Table(name = "number_of_hours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NumberOfHours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "month", nullable = false)
    private String month;

    @NotNull
    @Column(name = "external_agent_daily_hours_avg", nullable = false)
    private Double externalAgentDailyHoursAvg;

    @Column(name = "daily_hour_avg")
    private Double dailyHourAvg;

    @Column(name = "avg_hours_to_answer_calls")
    private Integer avgHoursToAnswerCalls;

    @Column(name = "total_hours_to_answer_calls")
    private Double totalHoursToAnswerCalls;

    @Column(name = "total_received_calls")
    private Integer totalReceivedCalls;

    @Column(name = "total_attended_calls")
    private Integer totalAttendedCalls;

    @Column(name = "attended_calls_percentage")
    private Double attendedCallsPercentage;

    @Column(name = "avg_daily_attended_calls")
    private Double avgDailyAttendedCalls;

    @Column(name = "avg_daily_attended_calls_by_external")
    private Double avgDailyAttendedCallsByExternal;

    @Column(name = "avg_daily_attended_calls_by_external_by_day")
    private Double avgDailyAttendedCallsByExternalByDay;

    @Column(name = "avg_daily_attended_calls_by_internal")
    private Double avgDailyAttendedCallsByInternal;

    @Column(name = "total_received_chats")
    private Integer totalReceivedChats;

    @Column(name = "total_attended_chats")
    private Integer totalAttendedChats;

    @Column(name = "total_received_mails")
    private Integer totalReceivedMails;

    @Column(name = "total_attended_mails")
    private Integer totalAttendedMails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NumberOfHours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return this.month;
    }

    public NumberOfHours month(String month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getExternalAgentDailyHoursAvg() {
        return this.externalAgentDailyHoursAvg;
    }

    public NumberOfHours externalAgentDailyHoursAvg(Double externalAgentDailyHoursAvg) {
        this.setExternalAgentDailyHoursAvg(externalAgentDailyHoursAvg);
        return this;
    }

    public void setExternalAgentDailyHoursAvg(Double externalAgentDailyHoursAvg) {
        this.externalAgentDailyHoursAvg = externalAgentDailyHoursAvg;
    }

    public Double getDailyHourAvg() {
        return this.dailyHourAvg;
    }

    public NumberOfHours dailyHourAvg(Double dailyHourAvg) {
        this.setDailyHourAvg(dailyHourAvg);
        return this;
    }

    public void setDailyHourAvg(Double dailyHourAvg) {
        this.dailyHourAvg = dailyHourAvg;
    }

    public Integer getAvgHoursToAnswerCalls() {
        return this.avgHoursToAnswerCalls;
    }

    public NumberOfHours avgHoursToAnswerCalls(Integer avgHoursToAnswerCalls) {
        this.setAvgHoursToAnswerCalls(avgHoursToAnswerCalls);
        return this;
    }

    public void setAvgHoursToAnswerCalls(Integer avgHoursToAnswerCalls) {
        this.avgHoursToAnswerCalls = avgHoursToAnswerCalls;
    }

    public Double getTotalHoursToAnswerCalls() {
        return this.totalHoursToAnswerCalls;
    }

    public NumberOfHours totalHoursToAnswerCalls(Double totalHoursToAnswerCalls) {
        this.setTotalHoursToAnswerCalls(totalHoursToAnswerCalls);
        return this;
    }

    public void setTotalHoursToAnswerCalls(Double totalHoursToAnswerCalls) {
        this.totalHoursToAnswerCalls = totalHoursToAnswerCalls;
    }

    public Integer getTotalReceivedCalls() {
        return this.totalReceivedCalls;
    }

    public NumberOfHours totalReceivedCalls(Integer totalReceivedCalls) {
        this.setTotalReceivedCalls(totalReceivedCalls);
        return this;
    }

    public void setTotalReceivedCalls(Integer totalReceivedCalls) {
        this.totalReceivedCalls = totalReceivedCalls;
    }

    public Integer getTotalAttendedCalls() {
        return this.totalAttendedCalls;
    }

    public NumberOfHours totalAttendedCalls(Integer totalAttendedCalls) {
        this.setTotalAttendedCalls(totalAttendedCalls);
        return this;
    }

    public void setTotalAttendedCalls(Integer totalAttendedCalls) {
        this.totalAttendedCalls = totalAttendedCalls;
    }

    public Double getAttendedCallsPercentage() {
        return this.attendedCallsPercentage;
    }

    public NumberOfHours attendedCallsPercentage(Double attendedCallsPercentage) {
        this.setAttendedCallsPercentage(attendedCallsPercentage);
        return this;
    }

    public void setAttendedCallsPercentage(Double attendedCallsPercentage) {
        this.attendedCallsPercentage = attendedCallsPercentage;
    }

    public Double getAvgDailyAttendedCalls() {
        return this.avgDailyAttendedCalls;
    }

    public NumberOfHours avgDailyAttendedCalls(Double avgDailyAttendedCalls) {
        this.setAvgDailyAttendedCalls(avgDailyAttendedCalls);
        return this;
    }

    public void setAvgDailyAttendedCalls(Double avgDailyAttendedCalls) {
        this.avgDailyAttendedCalls = avgDailyAttendedCalls;
    }

    public Double getAvgDailyAttendedCallsByExternal() {
        return this.avgDailyAttendedCallsByExternal;
    }

    public NumberOfHours avgDailyAttendedCallsByExternal(Double avgDailyAttendedCallsByExternal) {
        this.setAvgDailyAttendedCallsByExternal(avgDailyAttendedCallsByExternal);
        return this;
    }

    public void setAvgDailyAttendedCallsByExternal(Double avgDailyAttendedCallsByExternal) {
        this.avgDailyAttendedCallsByExternal = avgDailyAttendedCallsByExternal;
    }

    public Double getAvgDailyAttendedCallsByExternalByDay() {
        return this.avgDailyAttendedCallsByExternalByDay;
    }

    public NumberOfHours avgDailyAttendedCallsByExternalByDay(Double avgDailyAttendedCallsByExternalByDay) {
        this.setAvgDailyAttendedCallsByExternalByDay(avgDailyAttendedCallsByExternalByDay);
        return this;
    }

    public void setAvgDailyAttendedCallsByExternalByDay(Double avgDailyAttendedCallsByExternalByDay) {
        this.avgDailyAttendedCallsByExternalByDay = avgDailyAttendedCallsByExternalByDay;
    }

    public Double getAvgDailyAttendedCallsByInternal() {
        return this.avgDailyAttendedCallsByInternal;
    }

    public NumberOfHours avgDailyAttendedCallsByInternal(Double avgDailyAttendedCallsByInternal) {
        this.setAvgDailyAttendedCallsByInternal(avgDailyAttendedCallsByInternal);
        return this;
    }

    public void setAvgDailyAttendedCallsByInternal(Double avgDailyAttendedCallsByInternal) {
        this.avgDailyAttendedCallsByInternal = avgDailyAttendedCallsByInternal;
    }

    public Integer getTotalReceivedChats() {
        return this.totalReceivedChats;
    }

    public NumberOfHours totalReceivedChats(Integer totalReceivedChats) {
        this.setTotalReceivedChats(totalReceivedChats);
        return this;
    }

    public void setTotalReceivedChats(Integer totalReceivedChats) {
        this.totalReceivedChats = totalReceivedChats;
    }

    public Integer getTotalAttendedChats() {
        return this.totalAttendedChats;
    }

    public NumberOfHours totalAttendedChats(Integer totalAttendedChats) {
        this.setTotalAttendedChats(totalAttendedChats);
        return this;
    }

    public void setTotalAttendedChats(Integer totalAttendedChats) {
        this.totalAttendedChats = totalAttendedChats;
    }

    public Integer getTotalReceivedMails() {
        return this.totalReceivedMails;
    }

    public NumberOfHours totalReceivedMails(Integer totalReceivedMails) {
        this.setTotalReceivedMails(totalReceivedMails);
        return this;
    }

    public void setTotalReceivedMails(Integer totalReceivedMails) {
        this.totalReceivedMails = totalReceivedMails;
    }

    public Integer getTotalAttendedMails() {
        return this.totalAttendedMails;
    }

    public NumberOfHours totalAttendedMails(Integer totalAttendedMails) {
        this.setTotalAttendedMails(totalAttendedMails);
        return this;
    }

    public void setTotalAttendedMails(Integer totalAttendedMails) {
        this.totalAttendedMails = totalAttendedMails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberOfHours)) {
            return false;
        }
        return getId() != null && getId().equals(((NumberOfHours) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumberOfHours{" +
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
