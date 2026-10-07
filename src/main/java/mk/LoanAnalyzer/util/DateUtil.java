package mk.LoanAnalyzer.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * LocalDate and LocalDateTime util. Fixed to Europe/Prague timezone.
 *
 * @author fja
 */
public class DateUtil {

    public static final ZoneId LOCAL_ZONE_ID = ZoneId.of("Europe/Prague");

    /**
     * 31.12.2999
     */
    public static final LocalDate FAR_FUTURE = LocalDate.of(2999, 12, 31);
    /**
     * 1.1.1900
     */
    public static final LocalDate FAR_PAST = LocalDate.of(1900, 1, 1);

    /**
     * 31.12.2999
     */
    public static final OffsetDateTime FAR_FUTURE_TIME = OffsetDateTime.of(LocalDateTime.of(2999, 12, 31,0,0), ZoneOffset.MAX);

    /**
     * 1.1.1900
     */
    public static final OffsetDateTime FAR_PAST_TIME = OffsetDateTime.of(LocalDateTime.of(1900, 1, 1,0,0), ZoneOffset.MIN);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd. MM. yyyy HH:mm:ss");

    /**
     * 31.12.2999
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd. MM. yyyy");

    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("CS", "cz"));

    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * 13:25
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime toLocalDateTimeFromIso(String isoDate) {
        if (isoDate == null) return null;
        return fromInstantToLocalDateTime(Instant.parse(isoDate));
    }

    public static OffsetDateTime toOffsetDateTimeFromIso(String isoDateTime) {
        if (isoDateTime == null) return null;
        return OffsetDateTime.parse(isoDateTime);
    }

    public static LocalDate toLocalDateFromIso(String isoDate) {
        if (isoDate == null) return null;
        return fromInstantToLocalDate(Instant.parse(isoDate));
    }

    public static LocalDate toLocalDateFromIsoDate(String isoDate) {
        if (isoDate == null) return null;
        return LocalDate.parse(isoDate);
    }

    public static LocalDate toLocalDateFromFormatedDate(String formatedDate, String pattern) {
        if (formatedDate == null || formatedDate.trim().length() == 0) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(formatedDate.trim(), formatter);
    }

    public static LocalDateTime toLocalDateTimeFromFormatedDate(String formatedDate, String pattern) {
        if (formatedDate == null || formatedDate.trim().length() == 0) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(formatedDate.trim(), formatter);
    }

    public static String toIsoFromLocalDate(LocalDate date) {
        if (date == null) return null;
        return date.atStartOfDay().atZone(LOCAL_ZONE_ID).toInstant().toString();
    }

    public static String toIsoFromLocalDateTime(LocalDateTime date) {
        if (date == null) return null;
        return date.atZone(LOCAL_ZONE_ID).toInstant().toString();
    }

    public static String toIsoFromOffsetDateTime(OffsetDateTime date) {
        if (date == null) return null;
        // TODO Review, add some format string?
        // TODO Do we need toInstant()?
        return date.toInstant().toString();
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime dateTime) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(toIsoFromLocalDateTime(dateTime));
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static LocalDate getStartOfMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    public static LocalDate getStartOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate getStartOfMonth(YearMonth month) {
        return LocalDate.of(month.getYear(), month.getMonth().getValue(), 1);
    }

    public static LocalDate getEndOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    public static LocalDate getEndOfMonth(int year, int month) {
        LocalDate startOfMonth = getStartOfMonth(year, month);
        return startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
    }

    public static LocalDate getEndOfMonth(YearMonth month) {
        return getEndOfMonth(month.getYear(), month.getMonth().getValue());
    }

    public static OffsetDateTime getStartOfDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return OffsetDateTime.of(date, date.atStartOfDay().toLocalTime(), ZoneOffset.UTC); // TODO Review the zone, maybe input should be OffsetDateTime?
    }

    public static OffsetDateTime getStartOfDay(OffsetDateTime date) {
        return date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static OffsetDateTime getEndOfDay(LocalDate date) {
        return OffsetDateTime.of(date, date.atStartOfDay().with(LocalTime.MAX).toLocalTime(), ZoneOffset.UTC); // TODO Review the zone, maybe input should be OffsetDateTime?
    }

    public static LocalDate toUtcDate(OffsetDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDate();
    }

    public static OffsetDateTime getEndOfDay(OffsetDateTime date) {
        return date.withHour(23).withMinute(59).withSecond(59).withNano(999);
    }

    public static Tuple<Integer, Integer> getPrevYearMonth(Tuple<Integer, Integer> yearMonth) {
        return getPrevYearMonth(yearMonth.getA(), yearMonth.getB());
    }

    public static Tuple<Integer, Integer> getPrevYearMonth(int year, int month) {
        return month == 1 ? new Tuple<>(year - 1, 12) : new Tuple<>(year, month - 1);
    }

    public static Tuple<Integer, Integer> getNextYearMonth(int year, int month) {
        return month == 12 ? new Tuple<>(year + 1, 1) : new Tuple<>(year, month + 1);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) return null;
        return fromInstantToLocalDateTime(date.toInstant());
    }

    public static LocalDateTime toLocalDateTime(FileTime fileTime) {
        if (fileTime == null) return null;
        return fromInstantToLocalDateTime(fileTime.toInstant());
    }

    /**
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return fromInstantToLocalDate(date.toInstant());
    }

    public static LocalDate toLocalDate(Calendar calendar) {
        if (calendar == null) return null;
        return fromInstantToLocalDate(calendar.toInstant());
    }

    /**
     * @param d
     * @return
     */
    public static Date toDate(LocalDate d) {
        if (d == null) return null;
        Instant instant = d.atStartOfDay().atZone(LOCAL_ZONE_ID).toInstant();
        return Date.from(instant);
    }

    public static Date toDate(LocalDateTime d) {
        if (d == null) return null;
        Instant instant = d.atZone(LOCAL_ZONE_ID).toInstant();
        return Date.from(instant);
    }

    public static String formatDate(LocalDate d) {
        if (d == null) {
            return null;
        }
        return d.format(DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dt) {
        if (dt == null) {
            return null;
        }
        return dt.format(DATE_TIME_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dt, String pattern) {
        if (dt == null) {
            return null;
        }

        return dt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDateTime(OffsetDateTime dt) {
        if (dt == null) {
            return null;
        }
        return dt.format(DATE_TIME_FORMATTER);
    }

    public static String formatDateTime(OffsetDateTime dt, String pattern) {
        if (dt == null) {
            return null;
        }

        return dt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatTime(LocalDateTime dt) {
        if (dt == null) {
            return null;
        }
        return dt.format(TIME_FORMATTER);
    }

    public static String formatMonth(LocalDate d) {
        if (d == null) {
            return null;
        }
        return d.format(MONTH_FORMATTER);
    }

    public static String formatYearMonth(LocalDate d) {
        if (d == null) {
            return null;
        }
        return d.format(YEAR_MONTH_FORMATTER);
    }

    public static String formatYearMonth(YearMonth d) {
        if (d == null) {
            return null;
        }
        return d.format(YEAR_MONTH_FORMATTER);
    }

    private static LocalDate fromInstantToLocalDate(Instant instant) {
        return instant.atZone(LOCAL_ZONE_ID).toLocalDate();
    }

    private static LocalDateTime fromInstantToLocalDateTime(Instant instant) {
        return instant.atZone(LOCAL_ZONE_ID).toLocalDateTime();
    }

    public static Instant toInstant(LocalDateTime ldt) {
        return ldt.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static int toYearMonthInt(LocalDate date) {
        return date.getYear()*100 + date.getMonth().getValue();
    }

    public static long getDifferenceInDays(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    public static long getDifferenceInCalendarDays(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate());
    }

    public static long getDifferenceInCalendarDays(OffsetDateTime date1, OffsetDateTime date2) {
        return ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate());
    }

    public static long getDifferenceInDays(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    public static long getDifferenceInSeconds(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.SECONDS.between(date1, date2);
    }

    public static long getDifferenceInSeconds(OffsetDateTime date1, OffsetDateTime date2) {
        return ChronoUnit.SECONDS.between(date1, date2);
    }

    public static long getDifferenceInMillis(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.MILLIS.between(date1, date2);
    }

    public static long getDifferenceInMillis(OffsetDateTime date1, OffsetDateTime date2) {
        return ChronoUnit.MILLIS.between(date1, date2);
    }

    public static boolean isBetween(LocalDateTime dateTime, LocalDateTime from, LocalDateTime to) {
        return from.compareTo(dateTime) <= 0 && to.compareTo(dateTime) >= 0;
    }

    public static boolean isBetween(LocalDate dateTime, LocalDate from, LocalDate to) {
        return from.compareTo(dateTime) <= 0 && to.compareTo(dateTime) >= 0;
    }

    public static boolean isBeforeOrToday(LocalDate date, LocalDate today) {
        return date.isBefore(today) || date.isEqual(today);
    }

    public static boolean isToday(OffsetDateTime dateTime, OffsetDateTime moment) {
        if (dateTime == null || moment == null) {
            return false;
        }
        var utcDateTime = dateTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalDate();
        var utcMoment = moment.withOffsetSameInstant(ZoneOffset.UTC).toLocalDate();
        return utcDateTime.isEqual(utcMoment);
    }

    public static boolean isYesterday(OffsetDateTime dateTime, OffsetDateTime moment) {
        if (dateTime == null || moment == null) {
            return false;
        }
        var utcDateTime = dateTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalDate();
        var utcMoment = moment.withOffsetSameInstant(ZoneOffset.UTC).toLocalDate().minusDays(1);
        return utcDateTime.isEqual(utcMoment);
    }

    public static String formatTime(int time) {
        int hour = extractHour(time);
        int minute = extractMinute(time);
        return String.format("%d:%02d", hour, minute);
    }

    public static LocalDateTime intToTime(int intTime) { // TODO To OffsetDateTime?
        int hour = extractHour(intTime);
        int minute = extractMinute(intTime);
        LocalDateTime now = LocalDateTime.now();
        if (hour == 24) {
            hour = 0;
            now.plusDays(1);
        }
        return LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), hour, minute);
    }

    public static int timeToInt(LocalDateTime time) {
        if (time == null) {
            return 0;
        }
        int hour = time.getHour();
        int minute = time.getMinute();
        return makeIntTime(hour, minute);
    }

    public static int extractHour(int intTime) {
        return intTime / 100;
    }

    public static int extractMinute(int intTime) {
        return intTime % 100;
    }

    public static int makeIntTime(int hour, int minute) {
        return (hour * 100) + minute;
    }

    public static LocalDate parseDate(String s) {
        return parseDate(s, DATE_FORMATTER);
    }

    public static LocalDate parseDate(String s, DateTimeFormatter formatter) {
        return LocalDate.parse(s, formatter);
    }

    public static OffsetDateTime unixTimestampToOffsetDateTime(long timestamp) {
        if (timestamp > 9999999900l) {
            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("GMT"));
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("GMT"));
    }

}
