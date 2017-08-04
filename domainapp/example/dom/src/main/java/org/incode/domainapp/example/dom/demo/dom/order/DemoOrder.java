package org.incode.domainapp.example.dom.demo.dom.order;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Ordering;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleDemo"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject()
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@MemberGroupLayout(columnSpans = {6,0,0,6})
public class DemoOrder implements Comparable<DemoOrder> {

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence = "1")
    @MemberOrder(sequence = "1")
    @Getter @Setter
    private String number;


    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(prepend= ", ", sequence="2")
    @MemberOrder(sequence = "1")
    @Getter @Setter
    private String customerName;


    @javax.jdo.annotations.Column(allowsNull="true")
    @MemberOrder(sequence = "1")
    @Getter @Setter
    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    private LocalDate date;


    @javax.jdo.annotations.Column(allowsNull="true")
    @PropertyLayout(multiLine = 6)
    @MemberOrder(sequence = "4")
    @Getter @Setter
    private String preferences;



    @CollectionLayout(render = RenderType.EAGERLY)
    @Getter @Setter
    @javax.jdo.annotations.Persistent(mappedBy = "order")
    private SortedSet<DemoOrderLine> orderLines = new TreeSet<DemoOrderLine>();

    public SortedSet<DemoOrderLine> getOrderLines() {
        return orderLines;
    }



    @MemberOrder(name = "orderLines", sequence = "1")
    public DemoOrder add(
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Cost")
            final BigDecimal cost,
            @ParameterLayout(named="Quantity")
            final int quantity) {

        final DemoOrderLine orderLine = container.newTransientInstance(DemoOrderLine.class);
        orderLine.setCost(cost);
        orderLine.setDescription(description);
        orderLine.setQuantity(quantity);
        getOrderLines().add(orderLine); // will set the parent on the OrderLine

        container.persistIfNotAlready(orderLine);

        return this;
    }

    @MemberOrder(name = "orderLines", sequence = "2")
    public DemoOrder remove(final DemoOrderLine orderLine) {
        getOrderLines().remove(orderLine);
        return this;
    }

    public Collection<DemoOrderLine> choices0Remove() {
        return getOrderLines();
    }




    @Override
    public int compareTo(DemoOrder other) {
        return Ordering.natural().onResultOf(DemoOrder::getNumber).compare(this, other);
    }


    @javax.inject.Inject
    DomainObjectContainer container;


}
