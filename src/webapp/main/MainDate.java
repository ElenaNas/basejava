package webapp.main;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        Date date=new Date();
        System.out.println(date);
        System.out.println(start-System.currentTimeMillis());

        Calendar calendar=Calendar.getInstance();
        System.out.println(calendar.getTimeZone());
        System.out.println(calendar.getTime());

        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        System.out.println(localDate);
        System.out.println(localTime);
        LocalDateTime localDateTime=LocalDateTime.now();
        System.out.println(localDateTime);
        LocalDateTime ldtNow=LocalDateTime.of(localDate, localTime);
        System.out.println(ldtNow);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MMMM/yyyy");
        System.out.println(simpleDateFormat.format(date));

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd MMMM yyyy hh-mm-ss");
        System.out.println(dateTimeFormatter.format(ldtNow));
    }
}
