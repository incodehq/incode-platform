package org.incode.module.fixturesupport.dom.scripts;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixtures.FixtureClock;
import org.apache.isis.applib.fixturescripts.FixtureScript;

/**
 * @deprecated - use {@link org.apache.isis.applib.fixturescripts.clock.TickingClockFixture} instead
 */
@Deprecated
public class ClockFixture extends FixtureScript {

    //region > factory methods, constructors
    public static ClockFixture setTo(final String date) {
        return new ClockFixture(date);
    }

    private LocalDateTime localDateTime;
    private LocalDate localDate;

    public ClockFixture() {
        super(null, "clock");
    }

    public ClockFixture(String dateStr) {
        super(null, "clock");
        if(!parse(dateStr)) {
            throw new IllegalArgumentException(dateStr + " could not be parsed as a date/time or date");
        }
    }
    //endregion

    //region > parseAsLocalDateTime
    private boolean parse(String dateStr) {
        return dateStr == null || parseNonNull(dateStr);
    }

    private boolean parseNonNull(String dateStr) {
        this.localDateTime = parseAsLocalDateTime(dateStr);
        if(localDateTime == null) {
            this.localDate = parseAsLocalDate(dateStr);
        }
        return this.localDateTime != null || this.localDate != null;
    }

    private static LocalDate parseAsLocalDate(String dateStr) {
        for (DateTimeFormatter formatter : new DateTimeFormatter[]{
                DateTimeFormat.fullDateTime(),
                DateTimeFormat.mediumDateTime(),
                DateTimeFormat.shortDateTime(),
                DateTimeFormat.forPattern("yyyy-MM-dd"),
                DateTimeFormat.forPattern("yyyyMMdd"),
        }) {
            try {
                return formatter.parseLocalDate(dateStr);
            } catch (Exception e) {
                // continue;
            }
        }
        return null;
    }

    private static LocalDateTime parseAsLocalDateTime(String dateStr) {
        for (DateTimeFormatter formatter : new DateTimeFormatter[]{
                DateTimeFormat.fullDateTime(),
                DateTimeFormat.mediumDateTime(),
                DateTimeFormat.shortDateTime(),
                DateTimeFormat.forPattern("yyyyMMddhhmmss"),
                DateTimeFormat.forPattern("yyyyMMddhhmm")
        }) {
            try {
                return formatter.parseLocalDateTime(dateStr);
            } catch (Exception e) {
                // continue;
            }
        }
        return null;
    }
    //endregion

    @Override
    protected void execute(ExecutionContext fixtureResults) {
        if(!(Clock.getInstance() instanceof FixtureClock)) {
            throw new IllegalStateException("Clock has not been initialized as a FixtureClock");
        }
        final FixtureClock fixtureClock = (FixtureClock) FixtureClock.getInstance();

        if(localDateTime != null) {
            fixtureClock.setDate(localDateTime.getYear(), localDateTime.getMonthOfYear(), localDateTime.getDayOfMonth());
            fixtureClock.setTime(localDateTime.getHourOfDay(), localDateTime.getMinuteOfHour());
            return;
        }
        if(localDate != null) {
            fixtureClock.setDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
            return;
        }
    }

    @Override
    public String validateRun(String parameters) {
        return parseAsLocalDateTime(parameters) == null && parseAsLocalDate(parameters) == null
                ? "Parameter must be parseable as a date/time or as a date" : null;
    }
}
