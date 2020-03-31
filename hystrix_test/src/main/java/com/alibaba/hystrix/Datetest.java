package com.alibaba.hystrix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datetest {
    public static void main(String[] args) {
        String beginTime = "2018-07-28 14:42:32";
        String endTime = "2018-07-29 12:26:32";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = format.parse(beginTime);
            Date date2 = format.parse(endTime);

            int compareTo = date1.compareTo(date2);
            System.out.println(date1.before(date2));
            System.out.println(getDatePoor(date1, date2));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

}
