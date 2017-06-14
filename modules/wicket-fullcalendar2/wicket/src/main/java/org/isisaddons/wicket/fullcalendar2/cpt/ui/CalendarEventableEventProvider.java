package org.isisaddons.wicket.fullcalendar2.cpt.ui;

import com.google.common.base.Function;
import com.google.common.base.Objects;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;

public class CalendarEventableEventProvider extends EventProviderAbstract {

    private static final long serialVersionUID = 1L;

    public CalendarEventableEventProvider(
            final EntityCollectionModel collectionModel,
            final String calendarName) {
        super(collectionModel, calendarName);
    }

    @Override
    protected CalendarEvent calendarEventFor(
            final Object domainObject,
            final String calendarName) {
        if(domainObject == null || !(domainObject instanceof CalendarEventable)) {
            return null;
        }
        final CalendarEventable calendarEventable = (CalendarEventable)domainObject;
        return Objects.equal(calendarName, calendarEventable.getCalendarName())
                ? calendarEventable.toCalendarEvent()
                : null;
    }

    static final Function<ObjectAdapter, String> GET_CALENDAR_NAME = new Function<ObjectAdapter, String>() {
        @Override
        public String apply(final ObjectAdapter input) {
            final Object domainObject = input.getObject();
            if(domainObject == null || !(domainObject instanceof CalendarEventable)) {
                return null;
            }
            final CalendarEventable calendarEventable = (CalendarEventable) domainObject;
            if(calendarEventable == null) {
                return null;
            }
            return calendarEventable.getCalendarName();
        }
    };


}
