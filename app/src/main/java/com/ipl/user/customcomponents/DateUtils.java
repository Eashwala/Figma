package com.ipl.user.customcomponents;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static final String FORMAT_HH_MM_A = "hh:mm a";
    public static final String FORMAT_DD_MMM_HH_MM_A = "dd MMM, hh:mm a";
    private static final String FORMAT_DD_MMM_YYYY = "dd MMM yyyy";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_T_HH_MM_SSSZ = "yyyy-MM-dd'T'hh:mm:ss.SZ";
    private static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    private static String getDate(long millis){
        return getDate(millis, FORMAT_DD_MMM_YYYY);
    }

    public static String getDate(long millis, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(millis);
    }

    public static long getDOBTimeMillis(String date){
        return getTimeMillis(date, getMinDOBDate());
    }

    private static long getTimeMillis(String dateString, long minDOBDate) {
        if(!TextUtils.isEmpty(dateString) && TextUtils.isDigitsOnly(dateString))
        {
            return getISTMillis(Long.parseLong(dateString));
        }
        return minDOBDate;
    }

    public static String getDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        return getDate(calendar.getTimeInMillis());
    }

    public static String serverDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        return String.valueOf(calendar.getTimeInMillis());
    }

    public static long getMinDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTimeInMillis();
    }


    public static long getNextHourMillis(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTimeInMillis();
    }

    public static long getTomorrowMillis(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTimeInMillis();
    }

    public static long getNextWeekMillis(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return calendar.getTimeInMillis();
    }

    public static boolean isAfter(String dateToCompare, String refDate) {
        if(TextUtils.isEmpty(dateToCompare) || TextUtils.isEmpty(refDate) || !TextUtils.isDigitsOnly(dateToCompare)
                || !TextUtils.isDigitsOnly(refDate)){
            return true;
        }

        return Long.parseLong(refDate) > Long.parseLong(dateToCompare);
    }

    public static long getTimeMillis(String dateString) {
        if(!TextUtils.isEmpty(dateString) && TextUtils.isDigitsOnly(dateString)){
            return getISTMillis(Long.parseLong(dateString));
        }
        return System.currentTimeMillis();
    }

    public static String getDisplayFormat(String newDate) {
        try {
            if (!TextUtils.isEmpty(newDate)) {
                DateFormat outputFormat = new SimpleDateFormat(FORMAT_DD_MMM_YYYY, Locale.US);
                Date date = new Date(getTimeMillis(newDate));
                return outputFormat.format(date);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }



    public static String getRelativeTimeSpan(long millis, long nowMillis){
        long diff = nowMillis-millis;
        if(diff<android.text.format.DateUtils.SECOND_IN_MILLIS){
            return "just now";
        }
        return android.text.format.DateUtils.getRelativeTimeSpanString(millis, nowMillis, android.text.format.DateUtils.SECOND_IN_MILLIS).toString();

    }

    private static long getISTMillis(long millis) {

        TimeZone timeZone = TimeZone.getTimeZone("IST");
        return  millis+ timeZone.getOffset(System.currentTimeMillis());
    }



    public static long getMinDOBDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.YEAR, -18);
        return calendar.getTimeInMillis();
    }

    public static String getRelativeDateTimeSpan(Context context, long millis) {
        Calendar nowCal = Calendar.getInstance();

        Calendar thenCalendar = Calendar.getInstance();
        thenCalendar.setTimeInMillis(millis);
        if(thenCalendar.get(Calendar.DAY_OF_YEAR) == nowCal.get(Calendar.DAY_OF_YEAR)){
            return getDate(millis, FORMAT_HH_MM_A);
        }
//        else if(thenCalendar.get(Calendar.DAY_OF_YEAR) == nowCal.get(Calendar.DAY_OF_YEAR)-1){
//            return context.getString(R.string.yesterday);
//        }
        else{
            return getDate(millis, FORMAT_DD_MM_YYYY);
        }
    }

    private static String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        String todaydate = sdf.format(calendar.getTime());
        return todaydate;

    }

    public static String addoneweek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
       String dateOutput = format.format(date);
       return  dateOutput;

    }

    public static String addoneMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
       String todaydate = format.format(calendar.getTime());

        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        String  dateOutput = format.format(date);
return dateOutput;
    }

    public static String addoneYear() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        String todaydate = format.format(calendar.getTime());

        calendar.add(Calendar.YEAR, -1);
        Date date = calendar.getTime();
        String dateOutput = format.format(date);
        return dateOutput;
    }
}
