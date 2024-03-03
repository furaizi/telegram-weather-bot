package bot.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getFormattedTime(String textTime) {
        return format( parse(textTime) );
    }

    private static String format(LocalDateTime time) {
        return time.format(formatter);
    }

    private static LocalDateTime parse(String time) {
        return LocalDateTime.parse(time, parser);
    }
}
