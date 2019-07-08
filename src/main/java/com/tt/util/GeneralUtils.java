package com.tt.util;

import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TT on 2018/7/24.
 */
public class GeneralUtils {

    private static final Pattern MOBILE_REGEX = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(14[0-9])|(16[0-9])|(17[0-9])|(18([0-9]))|(19[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
    private static final Pattern EMAIL_REGEX=Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            Matcher matcher = EMAIL_REGEX.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;

    }

    /**
     * 验证手机号码
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile){
        boolean flag = false;
        try{
            Matcher matcher = MOBILE_REGEX.matcher(mobile);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;

    }

    public final static String md5(String s) {
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean judgeMapIsNotNll(Map<? extends String,? extends Object> map){

        if(map!=null &&!map.isEmpty() && map.size()>0){
            return true;
        }
        return false;
    }

    public static String getObjType(Object obj){
        if(obj == null){
            return null;
        }
        String typeName=obj.getClass().getName();
        int length= typeName.lastIndexOf(".");
        String type =typeName.substring(length+1);
        return type;
    }

}
