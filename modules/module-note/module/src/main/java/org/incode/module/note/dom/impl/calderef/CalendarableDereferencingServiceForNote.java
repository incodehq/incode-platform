package org.incode.module.note.dom.impl.calderef;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarableDereferencingService;

import org.incode.module.note.dom.impl.note.Note;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CalendarableDereferencingServiceForNote implements CalendarableDereferencingService {
    @Programmatic
	public Object dereference(final Object calendarableOrCalendarEventable) {
        return calendarableOrCalendarEventable instanceof Note?
                ((Note)calendarableOrCalendarEventable).getNotable():
                null;
    }
}
