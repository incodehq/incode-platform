package org.isisaddons.module.publishmq.dom.jdo.events;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;

@javax.jdo.annotations.PersistenceCapable(
        schema = "isispublishmq",
        table="OutboxEvent")
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "isispublishmq.OutboxEvent"
)
@DomainObjectLayout(named = "Outbox Event")
public class OutboxEvent extends PublishedEventAbstract {

}
