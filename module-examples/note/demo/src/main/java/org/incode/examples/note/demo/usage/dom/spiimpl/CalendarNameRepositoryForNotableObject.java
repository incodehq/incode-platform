package org.incode.examples.note.demo.usage.dom.spiimpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.example.note.dom.spi.CalendarNameRepository;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class CalendarNameRepositoryForNotableObject implements CalendarNameRepository {

    private final Map<Class<?>, List<String>> namesByClass = Maps.newHashMap();

    public CalendarNameRepositoryForNotableObject() {
        setCalendarNames(NotableObject.class, "BLUE", "GREEN", "RED");
    }

    @Programmatic
    public void setCalendarNames(final Class<?> cls, final String... names) {
        namesByClass.put(cls, Lists.newArrayList(names));
    }

    @Override
    public Collection<String> calendarNamesFor(final Object notable) {
        return namesByClass.get(notable.getClass());
    }
}
