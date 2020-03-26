package com.example.administrator.langues.activity.Matching.video;

public class CommonUtils {

        /**
      * 毫秒转换成小时
      * @param mss
      * @return
      */
        public static String formatDuring(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        String h ="";
        String m = "";
        String s = "";
        if(hours < 10){
        h = "0" + hours;
        }else{
        h = hours +  "";
        }

        if(minutes < 10){
        m = "0" + minutes;
        }else{
        m = minutes + "";
        }

        if(seconds < 10){
        s = "0" + seconds;
        }else{
        s = seconds + "";
        }

        return h + ":" + m + ":" + s;
        }
        }
