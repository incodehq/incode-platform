package org.isisaddons.module.command;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.command.dom.CommandDomModule;

/**
 * Note that referencing this module implicitly brings in the replay module if also on the class path.
 *
 * <p>
 *     Alternatively, use {@link CommandDomModule} and <tt>CommandReplayModule</tt> separately.
 * </p>
 */
@XmlRootElement(name = "module")
public class CommandModule extends ModuleAbstract {

    public abstract static class ActionDomainEvent<S>
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> { }

    public abstract static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> { }

    public abstract static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> { }
}
