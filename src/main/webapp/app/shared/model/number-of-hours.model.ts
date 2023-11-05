export interface INumberOfHours {
  id?: number;
  month?: string;
  externalAgentDailyHoursAvg?: number;
  dailyHourAvg?: number | null;
  avgHoursToAnswerCalls?: number | null;
  totalHoursToAnswerCalls?: number | null;
  totalReceivedCalls?: number | null;
  totalAttendedCalls?: number | null;
  attendedCallsPercentage?: number | null;
  avgDailyAttendedCalls?: number | null;
  avgDailyAttendedCallsByExternal?: number | null;
  avgDailyAttendedCallsByExternalByDay?: number | null;
  avgDailyAttendedCallsByInternal?: number | null;
  totalReceivedChats?: number | null;
  totalAttendedChats?: number | null;
  totalReceivedMails?: number | null;
  totalAttendedMails?: number | null;
}

export const defaultValue: Readonly<INumberOfHours> = {};
