package org.incode.domainapp.example.dom.lib.docx.dom.demo;

import java.math.BigDecimal;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleLibDocx"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@DomainObject()
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_CHILD
)
public class OrderLine implements Comparable<OrderLine> {

    //region > order (property)

    private Order order;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            hidden = Where.REFERENCES_PARENT
    )
    @MemberOrder(sequence = "1")
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }
    //endregion

    //region > name (property)

    private String description;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="2")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    //endregion

    //region > quantity (property)
    private int quantity;

    @MemberOrder(sequence = "3")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
    //endregion

    //region > cost (property)
    // //////////////////////////////////////
    private BigDecimal cost;

    @javax.jdo.annotations.Column(allowsNull="true", scale=2)
    @javax.validation.constraints.Digits(integer=10, fraction=2)
    @MemberOrder(sequence = "4")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(final BigDecimal cost) {
        this.cost = cost;
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(OrderLine other) {
        return ObjectContracts.compare(this, other, "description");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
