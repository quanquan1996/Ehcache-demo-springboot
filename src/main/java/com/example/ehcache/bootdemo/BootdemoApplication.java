package com.example.ehcache.bootdemo;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableCaching
public class BootdemoApplication {

    public static void main(String[] args) {
        System.out.println(getWeekStartDate());
        SpringApplication.run(BootdemoApplication.class, args);
    }
    public static Date getWeekStartDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        return date;
    }

}
