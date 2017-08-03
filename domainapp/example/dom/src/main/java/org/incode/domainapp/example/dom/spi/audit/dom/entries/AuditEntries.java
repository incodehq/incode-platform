package org.incode.domainapp.example.dom.spi.audit.dom.entries;

import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiAudit.AuditEntries"
)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        named = "Audit Entries"
)
public class AuditEntries {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            named = "List Audit Entries (demo)"
    )
    public List<AuditEntry> listAuditEntries(
            @ParameterLayout(named = "From")
            @Parameter(optionality = Optionality.OPTIONAL)
            final LocalDate from,
            @ParameterLayout(named = "To")
            @Parameter(optionality = Optionality.OPTIONAL)
            final LocalDate to) {
        return auditingServiceRepository.findByFromAndTo(from, to);
    }

    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;

}
