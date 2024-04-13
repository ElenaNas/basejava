package webapp.util;

import webapp.model.Company;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String formatDates(Company.Occupation occupation) {
        return DateUtil.format(occupation.getFromPeriod()) + " - " + DateUtil.format(occupation.getTillPeriod());
    }
}
