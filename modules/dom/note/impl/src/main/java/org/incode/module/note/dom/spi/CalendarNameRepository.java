package org.incode.module.note.dom.spi;

import java.util.Collection;

import org.incode.module.note.dom.impl.note.Note;

/**
 * Optional SPI service
 */
public interface CalendarNameRepository {

    /**
     * Return a collection of objects to act as calendars for the {@link Note}s to attach to the specified "notable"
     * domain object.
     *
     * <p>
     *     May return null if there are none (in which case a default name will be used).
     * </p>
     */
    Collection<String> calendarNamesFor(Object notable);

}
