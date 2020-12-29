package datadump.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 字符串工具类
 */
public class StringUtil extends StringUtils {
    /**
     * 获取32UUID字符串
     *
     * @param
     * @return String
     */
    public static String getUUIDStr() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取指定长度的随机数字
     *
     * @param length
     * @return String
     */
    public static String getRandomNum(int length) {
        String num = Math.random() + "";

        return num.substring(num.indexOf(".") + 1, num.indexOf(".") + length) + 1;
    }

    /**
     * 获取64位随机不重复字符串, UUID去"-" + 17位时间戳 + 15位数字
     *
     * @param
     * @return String
     */
    public static String get64RandomStr() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String timestamp = DateUtil.getCurrentDateString("yyyyMMddHHmmssSSS");
        String random = getRandomNum(15);

        return uuid + timestamp + random;
    }

    /**
     * 去除所有空格
     *
     * @param str
     * @return String
     */
    public static String removeBlank(String str) {

        return str.replaceAll("\\s*", "");
    }

    /**
     * 判断字符串为空, 包含null, "null", "", 空格
     *
     * @param str
     * @return boolean
     */
    public static boolean isNull(String str) {
        boolean flag = StringUtils.isBlank(str);

        // 当str不是null, "", 空格时, 判断是否为"null"
        if (!flag && "null".equals(str.replace(" ", ""))) {
            flag = true;
        }

        return flag;
    }

    /**
     * 判断字符串不为空, 包含null, "null", "", 空格
     *
     * @param str
     * @return boolean
     */
    public static boolean isNotNull(String str) {

        return !isNull(str);
    }



}
