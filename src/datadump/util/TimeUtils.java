package datadump.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat dateFormatNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatWithMS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
    private static final long time2009 = 1230739200000L + 28800000L;//beijing 2009-1-1

    public static String getTime(long time) {
        return time + "";
        //return String.format("%1$d", time);

    }

    public static String getTimeWithMS(long ss, long ms) {
        String str;
        if(ms<10)
            str = "00000" + ms;
        else if(ms>=10 && ms<100){
            str = "0000" + ms;
        }else if(ms>=100 && ms<1000){
            str = "000" + ms;
        }
        else if(ms>=1000 && ms<10000){
            str = "00" + ms;
        }else if(ms>=10000 && ms<100000){
            str = "0" + ms;
        }else{
            str = "" + ms;
        }
        return ss + "." + str;
        //return String.format("%1$d.%2$06d", ss, ms);
    }

    public static String now1() {
        return dateFormatWithMS.format(new Date());
    }
    public static String now() {
        return dateFormatNow.format(new Date());
    }

    public static String getTimeFromDoubleString(String str) {
        String[] split = str.split("[\\.]");
        long t = Long.parseLong(split[0]) * 1000L + time2009 + Long.parseLong(split[1]);
        return dateFormatWithMS.format(new Date(t));
    }

    public static String formatLong(String time) {
        long t = Long.parseLong(time);
        if (t <= 1000L) {
            return t + "";
        } else {
            long plus = t * 1000L + time2009;
            return dateFormat.format(new Date(plus));
        }
    }

    public static String formatDouble(String d) {
        if (Double.parseDouble(d) <= 1000d) {
            return d;
        } else {
            String[] date = d.split("[\\.]");
            long sec = Long.parseLong(date[0]) * 1000L + time2009;
            return dateFormat.format(new Date(sec))+ "." + date[1];
            //return String.format("%1$s.%2$s", dateFormat.format(new Date(sec)), date[1]);
        }
    }
    
    
}
