package datadump.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil extends DateUtils {

    /**
     * 获取当前日期字符串
     *
     * @param formatType
     * @return String
     */
    public static String getCurrentDateString(String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);

        return sdf.format(new Date());
    }

    /**
     * 获取指定日期若干秒之后的字符串
     *
     * @param timeStr, formatType, later(延迟秒数)
     * @return String
     */
    public static String getLaterDateString(String timeStr, String formatType,
                                            int later) {
        Date date = parseDate(timeStr, formatType);
        Calendar cnd = Calendar.getInstance();
        cnd.setTime(date);
        cnd.add(Calendar.SECOND, later);// timeStr时间增加later秒
        date = cnd.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);

        return sdf.format(date);
    }

    /**
     * 获取指定日期若干月之后的字符串
     *
     * @param timeStr, formatType, later(延迟月数)
     * @return String
     */
    public static String getMonthLaterDateString(String timeStr, String formatType, int later) {
        Date date = parseDate(timeStr, formatType);
        Calendar cnd = Calendar.getInstance();
        cnd.setTime(date);
        cnd.add(Calendar.MONTH, later);// timeStr时间增加later月
        date = cnd.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);

        return sdf.format(date);
    }
    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days,String sdf) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat(sdf);
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 将字符串解析成date
     *
     * @param timeStr, formatType
     * @return Date
     */
    public static Date parseDate(String timeStr, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        try {
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 对比当前时间和特定时间, >0则早于当前时间
     *
     * @param timeStr, formatType(特定时间的format格式)
     * @return String
     */
    public static int compareDate(String timeStr, String formatType) {
        Date curr = new Date();
        Date date = parseDate(timeStr, formatType);

        return curr.compareTo(date);
    }

    /**
     * 获取制定日期的字符串
     *
     * @param date, format
     * @return String
     */
    public static String getDateString(Date date, String format) {
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    /**
     * 获取年份
     *
     * @param date
     * @return String
     */
    public static String getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.YEAR) + "";
    }

    /**
     * 获取月份
     *
     * @param date
     * @return String
     */
    public static String getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.MONTH) + 1 + "";
    }

    /**
     * 获取日号
     *
     * @param date
     * @return String
     */
    public static String getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.DAY_OF_MONTH) + "";
    }
    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间  日期格式 yyyy-MM-dd
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     * calendar 对日期进行时间操作
     * getTimeInMillis() 获取日期的毫秒显示形式
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.parseDate(bdate,"yyyy-MM-dd"));
        long time1 = cal.getTimeInMillis();
        cal.setTime(DateUtil.parseDate(smdate,"yyyy-MM-dd"));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 根据生日获取年龄
     *
     * @param
     * @return String
     */
    public static String getAge(String birthday, String formatType) {
        Date date = parseDate(birthday, formatType);

        Calendar cal = Calendar.getInstance();
        if (cal.before(date)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(date);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return age + "";
    }

    public static String getCurrentDateLong(long currentTime, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(currentTime);

    }
}
