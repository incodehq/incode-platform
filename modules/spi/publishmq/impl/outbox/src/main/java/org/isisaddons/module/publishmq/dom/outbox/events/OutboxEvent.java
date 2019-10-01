package org.isisaddons.module.publishmq.dom.outbox.events;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;

@javax.jdo.annotations.PersistenceCapable(
        schema = "isispublishmq",
        table="OutboxEvent")
@javax.jdo.annotations.Queries( {
        @javax.jdo.annotations.Query(
                name="findByTransactionIdAndSequence", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.publishmq.dom.jdo.events.OutboxEvent "
                        + "WHERE transactionId == :transactionId "
                        + "&&    sequence      == :sequence "),
        @javax.jdo.annotations.Query(
                name="findOldest", language="JDOQL",
                value="SELECT "
                        + "FROM org.isisaddons.module.publishmq.dom.jdo.events.OutboxEvent "
                        + "ORDER BY timestamp ASC, transactionId ASC, sequence ASC "
                        + "RANGE 0,30")
})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "isispublishmq.OutboxEvent"
)
@DomainObjectLayout(named = "Outbox Event")
public class OutboxEvent extends PublishedEventAbstract {

}
