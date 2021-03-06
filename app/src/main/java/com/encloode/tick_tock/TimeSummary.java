package com.encloode.tick_tock;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author Riko Hamblin
 */
public class TimeSummary implements Serializable {

    public int[][] minutesWorked;
    public DateTime[][][] inTime;
    public DateTime[][][] outTime;
    public int[][] clockINCounter;
    public int[][] clockOUTCounter;
    static int numOfClockIn_OutAllowedPerDay = 40;


    public TimeSummary() {
        minutesWorked = new int[53][7];
        for (int i = 0; i < 53; i++)
            for (int j = 0; j < 7; j++) {
                minutesWorked[i][j] = 0;
            }
        inTime = new DateTime[53][7][numOfClockIn_OutAllowedPerDay]; //allows for 100 sign ins per day
        outTime = new DateTime[53][7][numOfClockIn_OutAllowedPerDay]; // allows for 100 sign outs per day
          for (int i = 0; i < 53; i++)
              for (int j = 0; j < 7; j++)
               for (int k = 0; k < numOfClockIn_OutAllowedPerDay; k++) {
                   inTime[i][j][k]= null;
                   outTime[i][j][k]= null;
            }

        clockINCounter = new int[53][7];
        clockOUTCounter = new int[53][7];
        for (int i = 0; i < 53; i++)
            for (int j = 0; j < 7; j++) {
                clockINCounter[i][j] = 0;
                clockOUTCounter[i][j] = 0;
            }

    }

    public int totalTimeDuringInterval(DateTime start, DateTime end) {
        int sum = 0;
        int weekOfYear = start.getWeekOfWeekyear();
        int dayOfWeek = start.getDayOfWeek();
        end = end.plusDays(1);
       while (start.isBefore(end)){
           sum += minutesWorked[weekOfYear-1][dayOfWeek-1];


           start = start.plusDays(1);
           weekOfYear = start.getWeekOfWeekyear();
           dayOfWeek = start.getDayOfWeek();
        }
        return sum;
    }

    public int totalHoursDuringInterval(DateTime start, DateTime end) {
        int totalMinutesWorked = totalTimeDuringInterval(start, end);

        return totalMinutesWorked/60;
    }

    public int totalMinutesDuringInterval(DateTime start, DateTime end) {
        int totalMinutesWorked = totalTimeDuringInterval(start, end);

        int minutesWorked = totalMinutesWorked - totalHoursDuringInterval(start, end) * 60;

        return minutesWorked;
    }

    public ArrayList<DateTime> getListOfDates (DateTime start, DateTime end){
        ArrayList<DateTime> dates = new ArrayList<>();

        DateTime date = start;

        while(date.isBefore(end)){
            dates.add(date);
            date = date.plusDays(1);
           }
        dates.add(end);

        return dates;

    }

    public ArrayList<DateTime> getListOfInTimes (DateTime date){
        ArrayList<DateTime> inTimes = new ArrayList<>();

        int weekWanted = date.getWeekOfWeekyear();
        int dayOfWeekWanted = date.getDayOfWeek();

        for (int i = 0; i < numOfClockIn_OutAllowedPerDay; i++)
            inTimes.add(inTime[weekWanted-1][dayOfWeekWanted-1][i]);

        return inTimes;
    }

    public ArrayList<DateTime> getListOfOutTimes (DateTime date){
        ArrayList<DateTime> outTimes = new ArrayList<>();

        int weekWanted = date.getWeekOfWeekyear();
        int dayOfWeekWanted = date.getDayOfWeek();

        for (int i = 0; i < numOfClockIn_OutAllowedPerDay; i++)
            outTimes.add(inTime[weekWanted-1][dayOfWeekWanted-1][i]);

        return outTimes;
    }

    public ArrayList<DateTime> getListOfIN_OUTTimes (DateTime date) {
        ArrayList<DateTime> times = new ArrayList<>();

        int weekWanted = date.getWeekOfWeekyear();
        int dayOfWeekWanted = date.getDayOfWeek();

        for (int i = 0; i < numOfClockIn_OutAllowedPerDay; i++) {
            times.add(inTime[weekWanted - 1][dayOfWeekWanted - 1][i]);
            times.add(outTime[weekWanted - 1][dayOfWeekWanted - 1][i]);
        }

        return times;
    }

    public boolean incrementINCounter (int week, int dayOfWeek) {

        if (clockINCounter[week-1][dayOfWeek-1] == numOfClockIn_OutAllowedPerDay)
            return false;
        else
            clockINCounter[week-1][dayOfWeek-1]++;
        return true;

    }

    public boolean incrementOUTCounter (int week, int dayOfWeek) {

        if (clockOUTCounter[week-1][dayOfWeek-1] == numOfClockIn_OutAllowedPerDay)
            return false;
        else
            clockOUTCounter[week-1][dayOfWeek-1]++;
        return true;

    }

    public int getINCountValue(int week, int day){
        return clockINCounter[week-1][day-1];
    }

    public int getOUTCountValue(int week, int day){
        return clockOUTCounter[week-1][day-1];
    }

    public void setINCountValue (int week, int day, int value){
        clockINCounter[week-1][day-1] = value;
    }

    public void setOUTCountValue (int week, int day, int value){
        clockOUTCounter[week-1][day-1] = value;
    }
}