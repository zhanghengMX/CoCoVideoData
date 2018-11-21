package com.henry.cocovideodata.jsoup;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by lzx on 2018/4/16.
 * Description :
 */

public class MuggulUtil {

    public static final String PREFIX_QQ = "http://v.qq.com";
    public static final String PREFIX_MGTV = "http://www.mgtv.com";
    public static final String PREFIX_YOUKU = "http://v.youku.com";
    public static final String PREFIX_IQIYI = "http://www.iqiyi.com";

    public static final String PREFIX_QQS = "https://v.qq.com";
    public static final String PREFIX_MGTVS = "https://www.mgtv.com";
    public static final String PREFIX_YOUKUS = "https://v.youku.com";
    public static final String PREFIX_IQIYIS = "https://www.iqiyi.com";

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static int tryParserInt(String s, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


}
