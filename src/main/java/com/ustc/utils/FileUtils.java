package com.ustc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    /**
     * 返回文件夹名称, 文件夹名称设为年月日
     * @return
     */
    public static String getFolder() {
        SimpleDateFormat formatYear=new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth=new SimpleDateFormat("MM");
        SimpleDateFormat formatDay=new SimpleDateFormat("dd");
        String year=formatYear.format(new Date());
        String month=formatMonth.format(new Date());
        String day=formatDay.format(new Date());

        return year+"/"+month+"/"+day;
    }
}
