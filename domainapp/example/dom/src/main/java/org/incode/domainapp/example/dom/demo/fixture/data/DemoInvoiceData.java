package org.incode.domainapp.example.dom.demo.fixture.data;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoice;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DemoInvoiceData implements DemoData<DemoInvoiceData, DemoInvoice> {

    Invoice1(1, new LocalDate(2017,1,31), 30, "/"),
    Invoice2(2, new LocalDate(2017,1,20), 60, "/ITA"),
    Invoice3(3, new LocalDate(2017,1,25), 90, "/FRA"),
    ;

    private final int num;
    private final LocalDate dueBy;
    private final int numDay;
    private final String atPath;

    @Programmatic
    public DemoInvoice persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public DemoInvoice findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.uniqueMatch(this, serviceRegistry);
    }

    @Programmatic
    public DemoInvoice asDomainObject() {
        return DemoInvoice.builder()
                    .num(num)
                    .dueBy(dueBy)
                    .numDays(numDay)
                    .atPath(atPath)
                    .build();
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, DemoInvoiceData, DemoInvoice> {
        public PersistScript() {
            super(DemoInvoiceData.class);
        }
    }


}
