package example.camelloadtest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CommonUtil {

    public static String toStr(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

	public static LocalDateTime toLocalDateTime(String date, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dtf);
    }

	public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zone);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

	public static Timestamp toTimestamp(LocalDateTime localDateTime) {
		return Timestamp.valueOf(localDateTime);
	}

	public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}
}
