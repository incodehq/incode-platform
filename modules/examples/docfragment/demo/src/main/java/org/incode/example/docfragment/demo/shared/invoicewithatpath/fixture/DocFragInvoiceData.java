package org.incode.example.docfragment.demo.shared.invoicewithatpath.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.docfragment.demo.shared.invoicewithatpath.dom.DocFragInvoice;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocFragInvoiceData implements DemoData<DocFragInvoiceData, DocFragInvoice> {

    Invoice1(1, new LocalDate(2017,1,31), 30, "/"),
    Invoice2(2, new LocalDate(2017,1,20), 60, "/ITA"),
    Invoice3(3, new LocalDate(2017,1,25), 90, "/FRA"),
    ;

    private final int num;
    private final LocalDate dueBy;
    private final int numDay;
    private final String atPath;

    @Programmatic
    public DocFragInvoice persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public DocFragInvoice findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.uniqueMatch(this, serviceRegistry);
    }

    @Programmatic
    public DocFragInvoice asDomainObject() {
        return DocFragInvoice.builder()
                    .num(num)
                    .dueBy(dueBy)
                    .numDays(numDay)
                    .atPath(atPath)
                    .build();
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, DocFragInvoiceData, DocFragInvoice> {
        public PersistScript() {
            super(DocFragInvoiceData.class);
        }
    }


}
