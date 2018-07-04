package org.incode.domainapp.extended.module.fixtures.per_cpt.ext.togglz;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;

@XmlRootElement(name = "module")
public class FixturesModuleExtTogglzSubmodule extends ModuleAbstract {

    public abstract static class ActionDomainEvent<S> extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {
    }

    public abstract static class CollectionDomainEvent<S,T> extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {
    }

    public abstract static class PropertyDomainEvent<S,T> extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {
    }
}
