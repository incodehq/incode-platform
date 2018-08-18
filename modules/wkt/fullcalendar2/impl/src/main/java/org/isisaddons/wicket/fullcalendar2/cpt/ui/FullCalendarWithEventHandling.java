package org.isisaddons.wicket.fullcalendar2.cpt.ui;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.oid.RootOid;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.isis.viewer.wicket.ui.pages.entity.EntityPage;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import net.ftlines.wicket.fullcalendar.CalendarResponse;
import net.ftlines.wicket.fullcalendar.Config;
import net.ftlines.wicket.fullcalendar.FullCalendar;
import net.ftlines.wicket.fullcalendar.callback.ClickedEvent;

final class FullCalendarWithEventHandling extends FullCalendar {
    
    @SuppressWarnings("unused")
	private final NotificationPanel feedback;
    private static final long serialVersionUID = 1L;

    FullCalendarWithEventHandling(
            final String id,
            final Config config,
            final NotificationPanel feedback) {
        super(id, config);
        this.feedback = feedback;
    }

    @Override
    protected void onEventClicked(
            final ClickedEvent event,
            final CalendarResponse response) {

        final String oidStr = (String) event.getEvent().getPayload();
        final RootOid oid = RootOid.deString(oidStr);
        final ObjectAdapter adapter = getPersistenceSession().adapterFor(oid);
        final PageParameters params = new EntityModel(adapter).getPageParameters();
        throw new RestartResponseException(EntityPage.class, params);
    }


    // //////////////////////////////////////

    protected PersistenceSession getPersistenceSession() {
        return IsisContext.getSessionFactory().getCurrentSession().getPersistenceSession();
    }

}
