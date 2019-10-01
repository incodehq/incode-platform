package org.isisaddons.module.publishmq.dom.jdo.events;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.isisaddons.module.publishmq.dom.outbox.events.PublishedEventAbstract;

/**
 * This entity is in the "incorrect" module because it shares a superclass with OutboxEvent.
 */
@javax.jdo.annotations.PersistenceCapable(
        schema = "isispublishmq",
        table="PublishedEvent")
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE transactionId == :transactionId "
                    + "ORDER BY timestamp DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTransactionIdAndSequence", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE transactionId == :transactionId "
                    + "&&    sequence      == :sequence "),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTarget", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE timestamp >= :from " 
                    + "&&    timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="find", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findRecentByUser", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE user == :user "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC "
                    + "RANGE 0,30"),
    @javax.jdo.annotations.Query(
            name="findRecentByTarget", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC "
                    + "RANGE 0,30")
})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "isispublishmq.PublishedEvent"
)
@DomainObjectLayout(named = "Published Event")
public class PublishedEvent extends PublishedEventAbstract {


}
