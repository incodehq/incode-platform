package org.isisaddons.module.publishmq.dom.outbox.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@XmlType(
        propOrder = {
                "events"
        }
)
@DomainObject(objectType = "isispublishmq.OutboxEvents")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class OutboxEvents {

    public String title() {
        return String.format("%d events", events.size());
    }

    @Collection
    @CollectionLayout(defaultView = "table")
    @XmlElementWrapper()
    @XmlElement(name="event")
    @Getter @Setter
    private List<OutboxEvent> events = new ArrayList<>();

}
