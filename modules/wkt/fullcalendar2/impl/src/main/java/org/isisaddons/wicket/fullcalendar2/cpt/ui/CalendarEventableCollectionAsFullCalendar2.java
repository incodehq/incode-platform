package org.isisaddons.wicket.fullcalendar2.cpt.ui;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;

import net.ftlines.wicket.fullcalendar.EventProvider;

public class CalendarEventableCollectionAsFullCalendar2 extends CalendaredCollectionAbstract {

    private static final long serialVersionUID = 1L;
    
    public CalendarEventableCollectionAsFullCalendar2(final String id, final EntityCollectionModel model) {
        super(id, model);
    }

    @Override
    protected EventProvider newEventProvider(
            final EntityCollectionModel model,
            final String calendarName) {
        return new CalendarEventableEventProvider(model, calendarName);
    }

    @Override
    protected Set<String> getCalendarNames(final Collection<ObjectAdapter> entityList) {
        return Sets.newLinkedHashSet(
                Iterables.transform(entityList, CalendarEventableEventProvider.GET_CALENDAR_NAME));
    }

}
