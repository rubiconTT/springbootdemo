package com.tt.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TT on 2018/12/14.
 */
public class DateUtils {

    /*
    *取得当前日期的前一天
    */
    public static Date getLastDay(){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /*
    *取得指定格式当前日期的前一天
    */
    public static String getLastDayStr(){
        Date date=getLastDay();
        String lastDayStr=date2String("yyyy-MM-dd",date);
        return lastDayStr;
    }

    /*
    *取得给定日期的前一天
    */
    public static Date getLastDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /*
    *取得当前日期
    */
    public static Date getCurrentDay(){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return date;
    }

    /*
    *取得当前日期
    */
    public static String getCurrentDayStr(){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return date2String("yyyy-MM-dd",date);
    }

    /*
    *取得当前日期的后一天
    */
    public static Date getNextDay(){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     *取得给当日期的前一天，号数
     * @param givenDate
     * @return
     */
    public static int getLastMonthDay(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.DAY_OF_MONTH,-1);

        int lastDay=calendar.get(Calendar.DAY_OF_MONTH);

        return lastDay;
    }

    /**
     *取得给当日期的后一天，号数
     * @param givenDate
     * @return
     */
    public static int getAfterMonthDay(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.DAY_OF_MONTH,1);

        int lastDay=calendar.get(Calendar.DAY_OF_MONTH);

        return lastDay;
    }

    /**
     *取得当前日期的前n天
     */
    public static String getLastDay(int n){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -n);
        date = calendar.getTime();
        return date2String("yyyy-MM-dd",date);
    }

    /**
     *取得给定日期的前n天
     */
    public static String getLastDay(Date date,int n){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -n);
        date = calendar.getTime();
        return date2String("yyyy-MM-dd",date);
    }

    /*
    *日期转string
    */
    public static String date2String(String dateFormat,Date date){
        DateFormat df=new SimpleDateFormat(dateFormat);
        String dateStr=null;
        try{
            dateStr=df.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateStr;
    }

    /*
    *string 转 date
    */
    public static Date string2Date(String dateFormat,String dateStr){
        DateFormat df=new SimpleDateFormat(dateFormat);
        Date date=new Date();
        try{
            date=df.parse(dateStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    /*
    *string 转 timestamp
    */
    public static Timestamp dateString2Timestamp(String dateStr){
        Timestamp ts=new Timestamp(System.currentTimeMillis());
        try{
            ts=Timestamp.valueOf(dateStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ts;
    }

    /*
    *timestamp convert to string
    */
    public static String timestamp2String(String dateFormat,Timestamp ts){
        DateFormat df=new SimpleDateFormat(dateFormat);
        String dateStr=null;
        try{
            dateStr=df.format(ts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateStr;
    }

    /*
    *date convert to timestamp
    */
    public static Timestamp date2Timestamp(Date date,String dateFormat){
        Timestamp ts=new Timestamp(System.currentTimeMillis());
        String dateStr=null;
        DateFormat df=new SimpleDateFormat(dateFormat);
        try{
            dateStr=df.format(date);
            ts=Timestamp.valueOf(dateStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ts;
    }

    /*
    *timestamp convert to date
    */
    public static Date timestamp2Date(Timestamp ts){
        Date date=ts;
        return date;
    }

    public static Long date2Milliseconds(Date date){
        Long milliseconds=date.getTime();
        return milliseconds;
    }

    public static Long dateStr2Milliseconds(String datestr){
        Date date=string2Date("yyyy-MM-dd HH:mm:ss",datestr);
        Long millis=date.getTime();
        return millis;
    }

    public static Long dateStr2Milliseconds(String dateFormat,String datestr){
        Date date=string2Date(dateFormat,datestr);
        Long millis=date.getTime();
        return millis;
    }

    public static Timestamp dateStr2Timestamp(String dateFormat,String datestr){
        Date date=string2Date(dateFormat,datestr);
        Long millis=date.getTime();
        Timestamp timestamp=new Timestamp(millis);
        return timestamp;
    }

    public static String milliseconds2DateStr(Long millis,String dateFormat){
        String millisStr=String.valueOf(millis);
        if(millisStr==null || millisStr.length()<13){
            return null;
        }
        String dateStr=null;
        Timestamp ts=new Timestamp(millis);
        DateFormat df=new SimpleDateFormat(dateFormat);
        try{
            dateStr=df.format(ts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateStr;
    }

    /*
    *获取当前时间的年月日时分秒组合字符串
    */
    public static String getCurrentTimeDetailString(){
        String detailStr=null;
        Calendar now = Calendar.getInstance();

        int month=now.get(Calendar.MONTH)+1;
        String monthStr=(month>=10) ? String.valueOf(month) : "0"+month;

        int day=now.get(Calendar.DAY_OF_MONTH);
        String dayStr=(day>=10) ? String.valueOf(day) : "0"+String.valueOf(day);

        int hour=now.get(Calendar.HOUR_OF_DAY);
        String hourStr=(hour>=10) ? String.valueOf(hour) : "0"+String.valueOf(hour);

        int miniute=now.get(Calendar.MINUTE);
        String miniuteStr=(miniute>=10) ? String.valueOf(miniute) : "0"+String.valueOf(miniute);

        int second=now.get(Calendar.SECOND);
        String secondStr=(second>=10) ? String.valueOf(second) : "0"+String.valueOf(second);

        detailStr=String.valueOf(now.get(Calendar.YEAR))
                +monthStr
                +dayStr
                +hourStr
                +miniuteStr
                +secondStr;

        return detailStr;
    }

    public static void main(String[] args) {
//        String dateStr="2018-03-01 16:53:55";
//        Date giveDate=string2Date("yyyy-MM-dd HH:mm:ss",dateStr);
////        Date giveDate=new Date();
//
//        int lastDay=getLastMonthDay(giveDate);
//        System.out.println(lastDay);
//        int afterDay=getAfterMonthDay(giveDate);
//        System.out.println(afterDay);

//        Timestamp timestamp=Timestamp.valueOf("1513521443000");
//        String time=timestamp2String("yyyy-MM-dd HH:mm:ss",timestamp);

        String time= milliseconds2DateStr(1513521443000l,"yyyy-MM-dd HH:mm:ss");

        System.out.println(time);
    }
}
