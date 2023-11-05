package com.dashboard.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dashboard.app.domain.NumberOfHours} entity. This class is used
 * in {@link com.dashboard.app.web.rest.NumberOfHoursResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /number-of-hours?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NumberOfHoursCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter month;

    private DoubleFilter externalAgentDailyHoursAvg;

    private DoubleFilter dailyHourAvg;

    private IntegerFilter avgHoursToAnswerCalls;

    private DoubleFilter totalHoursToAnswerCalls;

    private IntegerFilter totalReceivedCalls;

    private IntegerFilter totalAttendedCalls;

    private DoubleFilter attendedCallsPercentage;

    private DoubleFilter avgDailyAttendedCalls;

    private DoubleFilter avgDailyAttendedCallsByExternal;

    private DoubleFilter avgDailyAttendedCallsByExternalByDay;

    private DoubleFilter avgDailyAttendedCallsByInternal;

    private IntegerFilter totalReceivedChats;

    private IntegerFilter totalAttendedChats;

    private IntegerFilter totalReceivedMails;

    private IntegerFilter totalAttendedMails;

    private Boolean distinct;

    public NumberOfHoursCriteria() {}

    public NumberOfHoursCriteria(NumberOfHoursCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.externalAgentDailyHoursAvg = other.externalAgentDailyHoursAvg == null ? null : other.externalAgentDailyHoursAvg.copy();
        this.dailyHourAvg = other.dailyHourAvg == null ? null : other.dailyHourAvg.copy();
        this.avgHoursToAnswerCalls = other.avgHoursToAnswerCalls == null ? null : other.avgHoursToAnswerCalls.copy();
        this.totalHoursToAnswerCalls = other.totalHoursToAnswerCalls == null ? null : other.totalHoursToAnswerCalls.copy();
        this.totalReceivedCalls = other.totalReceivedCalls == null ? null : other.totalReceivedCalls.copy();
        this.totalAttendedCalls = other.totalAttendedCalls == null ? null : other.totalAttendedCalls.copy();
        this.attendedCallsPercentage = other.attendedCallsPercentage == null ? null : other.attendedCallsPercentage.copy();
        this.avgDailyAttendedCalls = other.avgDailyAttendedCalls == null ? null : other.avgDailyAttendedCalls.copy();
        this.avgDailyAttendedCallsByExternal =
            other.avgDailyAttendedCallsByExternal == null ? null : other.avgDailyAttendedCallsByExternal.copy();
        this.avgDailyAttendedCallsByExternalByDay =
            other.avgDailyAttendedCallsByExternalByDay == null ? null : other.avgDailyAttendedCallsByExternalByDay.copy();
        this.avgDailyAttendedCallsByInternal =
            other.avgDailyAttendedCallsByInternal == null ? null : other.avgDailyAttendedCallsByInternal.copy();
        this.totalReceivedChats = other.totalReceivedChats == null ? null : other.totalReceivedChats.copy();
        this.totalAttendedChats = other.totalAttendedChats == null ? null : other.totalAttendedChats.copy();
        this.totalReceivedMails = other.totalReceivedMails == null ? null : other.totalReceivedMails.copy();
        this.totalAttendedMails = other.totalAttendedMails == null ? null : other.totalAttendedMails.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NumberOfHoursCriteria copy() {
        return new NumberOfHoursCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMonth() {
        return month;
    }

    public StringFilter month() {
        if (month == null) {
            month = new StringFilter();
        }
        return month;
    }

    public void setMonth(StringFilter month) {
        this.month = month;
    }

    public DoubleFilter getExternalAgentDailyHoursAvg() {
        return externalAgentDailyHoursAvg;
    }

    public DoubleFilter externalAgentDailyHoursAvg() {
        if (externalAgentDailyHoursAvg == null) {
            externalAgentDailyHoursAvg = new DoubleFilter();
        }
        return externalAgentDailyHoursAvg;
    }

    public void setExternalAgentDailyHoursAvg(DoubleFilter externalAgentDailyHoursAvg) {
        this.externalAgentDailyHoursAvg = externalAgentDailyHoursAvg;
    }

    public DoubleFilter getDailyHourAvg() {
        return dailyHourAvg;
    }

    public DoubleFilter dailyHourAvg() {
        if (dailyHourAvg == null) {
            dailyHourAvg = new DoubleFilter();
        }
        return dailyHourAvg;
    }

    public void setDailyHourAvg(DoubleFilter dailyHourAvg) {
        this.dailyHourAvg = dailyHourAvg;
    }

    public IntegerFilter getAvgHoursToAnswerCalls() {
        return avgHoursToAnswerCalls;
    }

    public IntegerFilter avgHoursToAnswerCalls() {
        if (avgHoursToAnswerCalls == null) {
            avgHoursToAnswerCalls = new IntegerFilter();
        }
        return avgHoursToAnswerCalls;
    }

    public void setAvgHoursToAnswerCalls(IntegerFilter avgHoursToAnswerCalls) {
        this.avgHoursToAnswerCalls = avgHoursToAnswerCalls;
    }

    public DoubleFilter getTotalHoursToAnswerCalls() {
        return totalHoursToAnswerCalls;
    }

    public DoubleFilter totalHoursToAnswerCalls() {
        if (totalHoursToAnswerCalls == null) {
            totalHoursToAnswerCalls = new DoubleFilter();
        }
        return totalHoursToAnswerCalls;
    }

    public void setTotalHoursToAnswerCalls(DoubleFilter totalHoursToAnswerCalls) {
        this.totalHoursToAnswerCalls = totalHoursToAnswerCalls;
    }

    public IntegerFilter getTotalReceivedCalls() {
        return totalReceivedCalls;
    }

    public IntegerFilter totalReceivedCalls() {
        if (totalReceivedCalls == null) {
            totalReceivedCalls = new IntegerFilter();
        }
        return totalReceivedCalls;
    }

    public void setTotalReceivedCalls(IntegerFilter totalReceivedCalls) {
        this.totalReceivedCalls = totalReceivedCalls;
    }

    public IntegerFilter getTotalAttendedCalls() {
        return totalAttendedCalls;
    }

    public IntegerFilter totalAttendedCalls() {
        if (totalAttendedCalls == null) {
            totalAttendedCalls = new IntegerFilter();
        }
        return totalAttendedCalls;
    }

    public void setTotalAttendedCalls(IntegerFilter totalAttendedCalls) {
        this.totalAttendedCalls = totalAttendedCalls;
    }

    public DoubleFilter getAttendedCallsPercentage() {
        return attendedCallsPercentage;
    }

    public DoubleFilter attendedCallsPercentage() {
        if (attendedCallsPercentage == null) {
            attendedCallsPercentage = new DoubleFilter();
        }
        return attendedCallsPercentage;
    }

    public void setAttendedCallsPercentage(DoubleFilter attendedCallsPercentage) {
        this.attendedCallsPercentage = attendedCallsPercentage;
    }

    public DoubleFilter getAvgDailyAttendedCalls() {
        return avgDailyAttendedCalls;
    }

    public DoubleFilter avgDailyAttendedCalls() {
        if (avgDailyAttendedCalls == null) {
            avgDailyAttendedCalls = new DoubleFilter();
        }
        return avgDailyAttendedCalls;
    }

    public void setAvgDailyAttendedCalls(DoubleFilter avgDailyAttendedCalls) {
        this.avgDailyAttendedCalls = avgDailyAttendedCalls;
    }

    public DoubleFilter getAvgDailyAttendedCallsByExternal() {
        return avgDailyAttendedCallsByExternal;
    }

    public DoubleFilter avgDailyAttendedCallsByExternal() {
        if (avgDailyAttendedCallsByExternal == null) {
            avgDailyAttendedCallsByExternal = new DoubleFilter();
        }
        return avgDailyAttendedCallsByExternal;
    }

    public void setAvgDailyAttendedCallsByExternal(DoubleFilter avgDailyAttendedCallsByExternal) {
        this.avgDailyAttendedCallsByExternal = avgDailyAttendedCallsByExternal;
    }

    public DoubleFilter getAvgDailyAttendedCallsByExternalByDay() {
        return avgDailyAttendedCallsByExternalByDay;
    }

    public DoubleFilter avgDailyAttendedCallsByExternalByDay() {
        if (avgDailyAttendedCallsByExternalByDay == null) {
            avgDailyAttendedCallsByExternalByDay = new DoubleFilter();
        }
        return avgDailyAttendedCallsByExternalByDay;
    }

    public void setAvgDailyAttendedCallsByExternalByDay(DoubleFilter avgDailyAttendedCallsByExternalByDay) {
        this.avgDailyAttendedCallsByExternalByDay = avgDailyAttendedCallsByExternalByDay;
    }

    public DoubleFilter getAvgDailyAttendedCallsByInternal() {
        return avgDailyAttendedCallsByInternal;
    }

    public DoubleFilter avgDailyAttendedCallsByInternal() {
        if (avgDailyAttendedCallsByInternal == null) {
            avgDailyAttendedCallsByInternal = new DoubleFilter();
        }
        return avgDailyAttendedCallsByInternal;
    }

    public void setAvgDailyAttendedCallsByInternal(DoubleFilter avgDailyAttendedCallsByInternal) {
        this.avgDailyAttendedCallsByInternal = avgDailyAttendedCallsByInternal;
    }

    public IntegerFilter getTotalReceivedChats() {
        return totalReceivedChats;
    }

    public IntegerFilter totalReceivedChats() {
        if (totalReceivedChats == null) {
            totalReceivedChats = new IntegerFilter();
        }
        return totalReceivedChats;
    }

    public void setTotalReceivedChats(IntegerFilter totalReceivedChats) {
        this.totalReceivedChats = totalReceivedChats;
    }

    public IntegerFilter getTotalAttendedChats() {
        return totalAttendedChats;
    }

    public IntegerFilter totalAttendedChats() {
        if (totalAttendedChats == null) {
            totalAttendedChats = new IntegerFilter();
        }
        return totalAttendedChats;
    }

    public void setTotalAttendedChats(IntegerFilter totalAttendedChats) {
        this.totalAttendedChats = totalAttendedChats;
    }

    public IntegerFilter getTotalReceivedMails() {
        return totalReceivedMails;
    }

    public IntegerFilter totalReceivedMails() {
        if (totalReceivedMails == null) {
            totalReceivedMails = new IntegerFilter();
        }
        return totalReceivedMails;
    }

    public void setTotalReceivedMails(IntegerFilter totalReceivedMails) {
        this.totalReceivedMails = totalReceivedMails;
    }

    public IntegerFilter getTotalAttendedMails() {
        return totalAttendedMails;
    }

    public IntegerFilter totalAttendedMails() {
        if (totalAttendedMails == null) {
            totalAttendedMails = new IntegerFilter();
        }
        return totalAttendedMails;
    }

    public void setTotalAttendedMails(IntegerFilter totalAttendedMails) {
        this.totalAttendedMails = totalAttendedMails;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NumberOfHoursCriteria that = (NumberOfHoursCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(month, that.month) &&
            Objects.equals(externalAgentDailyHoursAvg, that.externalAgentDailyHoursAvg) &&
            Objects.equals(dailyHourAvg, that.dailyHourAvg) &&
            Objects.equals(avgHoursToAnswerCalls, that.avgHoursToAnswerCalls) &&
            Objects.equals(totalHoursToAnswerCalls, that.totalHoursToAnswerCalls) &&
            Objects.equals(totalReceivedCalls, that.totalReceivedCalls) &&
            Objects.equals(totalAttendedCalls, that.totalAttendedCalls) &&
            Objects.equals(attendedCallsPercentage, that.attendedCallsPercentage) &&
            Objects.equals(avgDailyAttendedCalls, that.avgDailyAttendedCalls) &&
            Objects.equals(avgDailyAttendedCallsByExternal, that.avgDailyAttendedCallsByExternal) &&
            Objects.equals(avgDailyAttendedCallsByExternalByDay, that.avgDailyAttendedCallsByExternalByDay) &&
            Objects.equals(avgDailyAttendedCallsByInternal, that.avgDailyAttendedCallsByInternal) &&
            Objects.equals(totalReceivedChats, that.totalReceivedChats) &&
            Objects.equals(totalAttendedChats, that.totalAttendedChats) &&
            Objects.equals(totalReceivedMails, that.totalReceivedMails) &&
            Objects.equals(totalAttendedMails, that.totalAttendedMails) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            month,
            externalAgentDailyHoursAvg,
            dailyHourAvg,
            avgHoursToAnswerCalls,
            totalHoursToAnswerCalls,
            totalReceivedCalls,
            totalAttendedCalls,
            attendedCallsPercentage,
            avgDailyAttendedCalls,
            avgDailyAttendedCallsByExternal,
            avgDailyAttendedCallsByExternalByDay,
            avgDailyAttendedCallsByInternal,
            totalReceivedChats,
            totalAttendedChats,
            totalReceivedMails,
            totalAttendedMails,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumberOfHoursCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (month != null ? "month=" + month + ", " : "") +
            (externalAgentDailyHoursAvg != null ? "externalAgentDailyHoursAvg=" + externalAgentDailyHoursAvg + ", " : "") +
            (dailyHourAvg != null ? "dailyHourAvg=" + dailyHourAvg + ", " : "") +
            (avgHoursToAnswerCalls != null ? "avgHoursToAnswerCalls=" + avgHoursToAnswerCalls + ", " : "") +
            (totalHoursToAnswerCalls != null ? "totalHoursToAnswerCalls=" + totalHoursToAnswerCalls + ", " : "") +
            (totalReceivedCalls != null ? "totalReceivedCalls=" + totalReceivedCalls + ", " : "") +
            (totalAttendedCalls != null ? "totalAttendedCalls=" + totalAttendedCalls + ", " : "") +
            (attendedCallsPercentage != null ? "attendedCallsPercentage=" + attendedCallsPercentage + ", " : "") +
            (avgDailyAttendedCalls != null ? "avgDailyAttendedCalls=" + avgDailyAttendedCalls + ", " : "") +
            (avgDailyAttendedCallsByExternal != null ? "avgDailyAttendedCallsByExternal=" + avgDailyAttendedCallsByExternal + ", " : "") +
            (avgDailyAttendedCallsByExternalByDay != null ? "avgDailyAttendedCallsByExternalByDay=" + avgDailyAttendedCallsByExternalByDay + ", " : "") +
            (avgDailyAttendedCallsByInternal != null ? "avgDailyAttendedCallsByInternal=" + avgDailyAttendedCallsByInternal + ", " : "") +
            (totalReceivedChats != null ? "totalReceivedChats=" + totalReceivedChats + ", " : "") +
            (totalAttendedChats != null ? "totalAttendedChats=" + totalAttendedChats + ", " : "") +
            (totalReceivedMails != null ? "totalReceivedMails=" + totalReceivedMails + ", " : "") +
            (totalAttendedMails != null ? "totalAttendedMails=" + totalAttendedMails + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
