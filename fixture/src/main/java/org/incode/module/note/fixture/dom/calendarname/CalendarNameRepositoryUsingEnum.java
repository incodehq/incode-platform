package org.incode.module.note.fixture.dom.calendarname;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.note.dom.note.CalendarNameRepository;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class CalendarNameRepositoryUsingEnum implements CalendarNameRepository {

    @Override
    public Collection<String> calendarNamesFor(final Object notable) {
        if(notable instanceof NoteDemoObject) {
            return Lists.transform(
                        Arrays.asList(CalendarName.values()),
                        input -> input.toString());
        }
        return null;
    }
}
