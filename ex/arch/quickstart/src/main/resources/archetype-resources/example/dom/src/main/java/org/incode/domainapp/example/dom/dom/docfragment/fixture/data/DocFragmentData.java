#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.docfragment.fixture.data;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.docfragment.dom.impl.DocFragment;
import org.incode.example.docfragment.dom.impl.DocFragmentRepository;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum DocFragmentData implements DemoData<DocFragmentData, DocFragment> {

    Customer_hello_GLOBAL("exampledemo.DemoCustomer", "hello", "/", "Hello, nice to meet you, ${symbol_dollar}{title} ${symbol_dollar}{lastName}"),
    Customer_hello_ITA("exampledemo.DemoCustomer", "hello", "/ITA", "Ciao, piacere di conoscerti, ${symbol_dollar}{title} ${symbol_dollar}{lastName}"),
    Customer_hello_FRA("exampledemo.DemoCustomer", "hello", "/FRA", "Bonjour, ${symbol_dollar}{title} ${symbol_dollar}{lastName}, agréable de vous rencontrer"),
    Customer_goodbye_GLOBAL("exampledemo.DemoCustomer", "goodbye", "/", "So long, ${symbol_dollar}{firstName}"),
    Invoice_due_GLOBAL("exampledemo.DemoInvoiceWithAtPath", "due", "/", "The invoice will be due on the ${symbol_dollar}{dueBy}, payable in ${symbol_dollar}{numDays} days"),
    Invoice_due_FRA("exampledemo.DemoInvoiceWithAtPath", "due", "/FRA", "La facture sera due sur le ${symbol_dollar}{dueBy}, payable dans ${symbol_dollar}{numDays} jours");

    private final String objectType;
    private final String name;
    private final String atPath;
    private final String templateText;

    @Programmatic
    public DocFragment asDomainObject() {
        return DocFragment.builder()
                .objectType(this.getObjectType())
                .name(this.getName())
                .atPath(this.getAtPath())
                .templateText(this.getTemplateText())
                .build();
    }

    @Programmatic
    public DocFragment persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public DocFragment createWith(final DocFragmentRepository repository) {
        return repository.create(getObjectType(), getName(), getAtPath(), getTemplateText());
    }

    @Programmatic
    public DocFragment findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.firstMatch(this, serviceRegistry);
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, DocFragmentData, DocFragment> {
        public PersistScript() {
            super(DocFragmentData.class);
        }
    }

}
