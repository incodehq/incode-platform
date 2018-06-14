package org.isisaddons.wicket.fullcalendar2.cpt.applib;

import com.google.common.base.Function;

public interface CalendarEventable {

    /**
     * The name of the calendar to which this event belongs.
     *
     * <p>
     *     For example, an <code>Employee</code> might provide have a <code>employedOn</code>, so the calendar name
     *     would be &quot;employedOn&quot;
     * </p>
     *
     * <p>
     *     If there is possibly more than one date associated with the entity, then use
     *     {@link org.isisaddons.wicket.fullcalendar2.cpt.applib.Calendarable} instead.
     * </p>
     */
    String getCalendarName();

    CalendarEvent toCalendarEvent();

    public static class Functions  {
        private Functions(){}

        public static <T extends CalendarEventable> Function<T, String> getCalendarName() {
            return new Function<T, String>(){
                @Override
                public String apply(T calendarEventable) {
                    return calendarEventable.getCalendarName();
                }};
        }

        public static <T extends CalendarEventable> Function<T, CalendarEventable> cast() {
            return new Function<T, CalendarEventable>(){
                @Override
                public CalendarEventable apply(CalendarEventable toDoItem) {
                    return toDoItem;
                }
            };
        }
    }

}
