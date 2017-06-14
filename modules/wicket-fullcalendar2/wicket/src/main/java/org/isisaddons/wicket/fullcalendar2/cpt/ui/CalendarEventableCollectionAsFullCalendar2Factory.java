package org.isisaddons.wicket.fullcalendar2.cpt.ui;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;

import org.apache.wicket.Component;

import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;

/**
 * {@link ComponentFactory} for {@link CalendarEventableCollectionAsFullCalendar2}.
 */
public class CalendarEventableCollectionAsFullCalendar2Factory extends CalendaredCollectionFactoryAbstract {

    private static final long serialVersionUID = 1L;

    public CalendarEventableCollectionAsFullCalendar2Factory() {
        super(CalendarEventable.class);
    }

    @Override
    protected Component newComponent(String id, EntityCollectionModel collectionModel) {
        return new CalendarEventableCollectionAsFullCalendar2(id, collectionModel);
    }
}
