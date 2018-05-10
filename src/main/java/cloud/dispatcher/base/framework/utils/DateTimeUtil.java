package cloud.dispatcher.base.framework.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public final class DateTimeUtil {

    private DateTimeUtil() {}

    public static String getYesterday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date yesterday = new Date(System.currentTimeMillis() - 86400000);
        return dateFormat.format(yesterday);
    }

    public static String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getNow(DateTimeFormatter format) {
        if (Objects.isNull(format)) { return getNow(); }
        return LocalDateTime.now().format(format);
    }

    public static String getNow(String format) {
        if (Objects.isNull(format)) { return getNow(); }
        return getNow(DateTimeFormatter.ofPattern(format));
    }
}
