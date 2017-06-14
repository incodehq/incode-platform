package org.isisaddons.wicket.fullcalendar2.cpt.ui;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.Calendarable;

import org.apache.wicket.Component;

import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;

/**
 * {@link ComponentFactory} for {@link CalendarableCollectionAsFullCalendar2}.
 */
public class CalendarableCollectionAsFullCalendar2Factory extends CalendaredCollectionFactoryAbstract {

    private static final long serialVersionUID = 1L;

    public CalendarableCollectionAsFullCalendar2Factory() {
        super(Calendarable.class);
    }

    @Override
    protected Component newComponent(String id, EntityCollectionModel collectionModel) {
        return new CalendarableCollectionAsFullCalendar2(id, collectionModel);
    }
}
