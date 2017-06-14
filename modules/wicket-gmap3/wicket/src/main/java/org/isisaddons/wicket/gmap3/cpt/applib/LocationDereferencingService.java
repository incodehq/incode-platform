package org.isisaddons.wicket.gmap3.cpt.applib;

import org.apache.isis.applib.annotation.Programmatic;

/**
 * Optional SPI service that allows a {@link Locatable} object to be translated/dereferenced to some other object, typically its owner.  The markers on the map then open up the dereferenced object, rather than the <tt>Locatable</tt>.
 *
 * <p>
 *     For example, the <tt>incode-module-commchannel</tt>'s <tt>PostalAddress</tt> implements <tt>Locatable</tt>, but this service allows the <i>owner</i> of the
 *     <tt>PostalAddress</tt> to be shown instead.
 * </p>
 *
 */
public interface LocationDereferencingService {
    @Programmatic
	Object dereference(final Object locatable);
}
