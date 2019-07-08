package com.tt.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TT on 2018/9/19.
 */
public class StringUtils {

    private static final String DATE_PATTERN="yyyy-MM-dd HH:mm:ss";

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){

        boolean isNotBlank=org.apache.commons.lang.StringUtils.isNotBlank(str);

        if(str!=null && ! "".equals(str)
                && str.length()>0 && !str.isEmpty()
                && !"null".equals(str) && isNotBlank){
            return true;
        }
        return false;
    }


    /**
     * 判断字符A与字符串B是否相等
     * @param strA
     * @param strB
     * @return
     */
    public static boolean judgeEqualOrNot(String strA,String strB){

        //字符串同时非空
        if(isNotEmpty(strA) && isNotEmpty(strB)){
            if(strA.equals(strB)){
                return true;
            }
        }
        //字符串同时为空
        if(!isNotEmpty(strA) && !isNotEmpty(strB)){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否存在空格，并清空空格
     * @param str
     * @return
     */
    public static String checkBlank(String str){
        if(!isNotEmpty(str)){
            return "";
        }
        str=str.replaceAll("\\s*", "");
        return str;
    }

    public static String blob2String(Blob blob){
        String blobStr=null;
        InputStream is=null;
        ByteArrayOutputStream result =null;
        try {
            if(blob==null || blob.length()<=0){
                return null;
            }
            is=blob.getBinaryStream();
            result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024*2];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            result.flush();
            blobStr= new String(result.toByteArray(),StandardCharsets.UTF_8.name());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null){
                    is.close();
                }
                if(result!=null){
                    result.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return blobStr;
    }



    public static Blob string2Blob(String str){
        if(!isNotEmpty(str)){
            return null;
        }
        Blob blob=null;
        InputStream is=new ByteArrayInputStream(str.getBytes());
        return blob;
    }

    public static String date2String(Date date){
        String dateStr=null;
        if(date!=null ){
            DateFormat df=new SimpleDateFormat(DATE_PATTERN);
            dateStr=df.format(date);
        }
        return dateStr;
    }
}
