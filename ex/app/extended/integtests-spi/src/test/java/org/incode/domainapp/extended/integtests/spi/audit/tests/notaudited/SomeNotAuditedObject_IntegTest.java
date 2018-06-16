package org.incode.domainapp.extended.integtests.spi.audit.tests.notaudited;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;

import org.incode.domainapp.extended.integtests.spi.audit.AuditModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.SomeAuditedObject_and_SomeNonAuditedObject_recreate3;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SomeNotAuditedObject_IntegTest extends AuditModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new SomeAuditedObject_and_SomeNonAuditedObject_recreate3(), null);
        transactionService.nextTransaction();
    }

    @Inject
    SomeNotAuditedObjects someNotAuditedObjects;

    @Inject
    IsisJdoSupport isisJdoSupport;

    @Inject
    AuditingServiceRepository auditingServiceRepository;

    @Inject
    BookmarkService bookmarkService;

    @Test
    public void auditEntriesNotCreatedOnCommit() throws Exception {

        // given
        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        transactionService.flushTransaction();

        // when
        wrap(someNotAuditedObjects).createSomeNotAuditedObject("Faz");

        // currently necessary to ensure that created objects are picked up and enlisted in the transaction as newly created
        transactionService.flushTransaction();

        // then
        final List<Map<String, Object>> prior = isisJdoSupport.executeSql("SELECT * FROM \"isisaudit\".\"AuditEntry\"");
        Assert.assertThat(prior.size(), is(0));

        // when
        transactionService.nextTransaction();

        // then
        Assert.assertThat(prior.size(), is(0));
    }


    @Test
    public void update() throws Exception {

        // given
        final SomeNotAuditedObject someNotAuditedObject = someNotAuditedObjects.listAllSomeNotAuditedObjects().get(0);
        final Bookmark bookmark = bookmarkService.bookmarkFor(someNotAuditedObject);

        assertThat(someNotAuditedObject.getName(), is("Foo"));
        assertThat(someNotAuditedObject.getNumber(), is(nullValue()));

        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        transactionService.flushTransaction();

        // when
        someNotAuditedObject.setName("Bob");
        someNotAuditedObject.setNumber(123);

        transactionService.nextTransaction();

        // then

        final List<AuditEntry> auditEntries = sorted(auditingServiceRepository.findByTargetAndFromAndTo(bookmark, null, null));
        assertThat(auditEntries.size(), is(0));

    }

    private static List<AuditEntry> sorted(List<AuditEntry> auditEntries) {
        Collections.sort(auditEntries,
                Comparator.comparing(AuditEntry::getMemberIdentifier));
        return auditEntries;
    }


}