/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.wicket.fullcalendar2.cpt.ui;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.mgr.AdapterManager;
import org.apache.isis.core.metamodel.adapter.oid.OidMarshaller;
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
