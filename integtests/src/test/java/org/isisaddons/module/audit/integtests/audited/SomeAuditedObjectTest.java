/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.audit.integtests.audited;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.audit.AuditingService3;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;

import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;
import org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObjects;
import org.isisaddons.module.audit.fixture.scripts.AuditDemoAppFixture;
import org.isisaddons.module.audit.integtests.AuditModuleIntegTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class SomeAuditedObjectTest extends AuditModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new AuditDemoAppFixture());
    }

    @Inject
    private SomeAuditedObjects someAuditedObjects;

    @Inject
    private IsisJdoSupport isisJdoSupport;

    @Inject
    private AuditingService3 auditingService3;

    @Inject
    private AuditingServiceRepository auditingServiceRepository;

    @Inject
    private BookmarkService bookmarkService;

    @Inject
    private TransactionService transactionService;

    @Test
    public void auditEntriesCreatedOnCommit() throws Exception {

        // given
        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        transactionService.flushTransaction();

        // when
        wrap(someAuditedObjects).create("Faz");

        // currently necessary to ensure that created objects are picked up and enlisted in the transaction as newly created
        transactionService.flushTransaction();

        // then
        final List<Map<String, Object>> prior = isisJdoSupport.executeSql("SELECT * FROM \"isisaudit\".\"AuditEntry\"");
        Assert.assertThat(prior.size(), is(0));

        // when
        this.nextTransaction();

        // then
        final List<Map<String, Object>> after = isisJdoSupport.executeSql("SELECT * FROM \"isisaudit\".\"AuditEntry\"");
        assertThat(after.size(), is(2));
    }

    @Test
    public void create() throws Exception {

        // given
        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        transactionService.flushTransaction();

        // when
        final SomeAuditedObject newObject = wrap(someAuditedObjects).create("Faz");

        // currently necessary to ensure that created objects are picked up and enlisted in the transaction as newly created
        transactionService.flushTransaction();

        this.nextTransaction();

        // then
        final Bookmark bookmark = bookmarkService.bookmarkFor(newObject);

        final List<Map<String, Object>> after = isisJdoSupport.executeSql("SELECT * FROM \"isisaudit\".\"AuditEntry\"");
        assertThat(after.size(), is(2));

        final List<AuditEntry> auditEntries = sorted(auditingServiceRepository.findByTargetAndFromAndTo(bookmark, null, null));
        assertThat(auditEntries.size(), is(2));

        final AuditEntry auditEntry1 = auditEntries.get(0);

        final Timestamp timestamp = auditEntry1.getTimestamp();
        final UUID transactionId = auditEntry1.getTransactionId();

        assertThat(auditEntry1.getUser(), is("tester"));
        assertThat(timestamp, is(notNullValue()));
        assertThat(transactionId, is(notNullValue()));
        assertThat(auditEntry1.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry1.getTargetStr(), is("isisauditdemo.SomeAuditedObject:3"));
        assertThat(auditEntry1.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject#name"));
        assertThat(auditEntry1.getPropertyId(), is("name"));
        assertThat(auditEntry1.getPreValue(), is("[NEW]"));
        assertThat(auditEntry1.getPostValue(), is("Faz"));
        assertThat(auditEntry1.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));

        final AuditEntry auditEntry2 = auditEntries.get(1);

        assertThat(auditEntry2.getUser(), is("tester"));
        final Timestamp timestamp2 = auditEntry2.getTimestamp();
        assertThat(timestamp2, is(timestamp));
        final UUID transactionId2 = auditEntry1.getTransactionId();
        assertThat(transactionId2, is(transactionId));
        assertThat(auditEntry2.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry2.getTargetStr(), is("isisauditdemo.SomeAuditedObject:3"));
        assertThat(auditEntry2.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject#number"));
        assertThat(auditEntry2.getPropertyId(), is("number"));
        assertThat(auditEntry2.getPreValue(), is("[NEW]"));
        assertThat(auditEntry2.getPostValue(), is(nullValue()));
        assertThat(auditEntry2.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));
    }


    @Test
    public void update() throws Exception {

        // given
        final SomeAuditedObject someAuditedObject = someAuditedObjects.listAll().get(0);
        final Bookmark bookmark = bookmarkService.bookmarkFor(someAuditedObject);

        assertThat(someAuditedObject.getName(), is("Foo"));
        assertThat(someAuditedObject.getNumber(), is(nullValue()));

        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        transactionService.flushTransaction();

        // when
        someAuditedObject.setName("Bob");
        someAuditedObject.setNumber(123);

        this.nextTransaction();

        // then

        final List<AuditEntry> auditEntries = sorted(auditingServiceRepository.findByTargetAndFromAndTo(bookmark, null, null));
        assertThat(auditEntries.size(), is(2));

        final AuditEntry auditEntry1 = auditEntries.get(0);

        final Timestamp timestamp = auditEntry1.getTimestamp();
        final UUID transactionId = auditEntry1.getTransactionId();

        assertThat(auditEntry1.getUser(), is("tester"));
        assertThat(timestamp, is(notNullValue()));
        assertThat(transactionId, is(notNullValue()));
        assertThat(auditEntry1.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry1.getTargetStr(), startsWith("isisauditdemo.SomeAuditedObject:"));
        assertThat(auditEntry1.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject#name"));
        assertThat(auditEntry1.getPropertyId(), is("name"));
        assertThat(auditEntry1.getPreValue(), is("Foo"));
        assertThat(auditEntry1.getPostValue(), is("Bob"));
        assertThat(auditEntry1.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));

        final AuditEntry auditEntry2 = auditEntries.get(1);

        assertThat(auditEntry2.getUser(), is("tester"));
        final Timestamp timestamp2 = auditEntry2.getTimestamp();
        assertThat(timestamp2, is(timestamp));
        final UUID transactionId2 = auditEntry1.getTransactionId();
        assertThat(transactionId2, is(transactionId));
        assertThat(auditEntry2.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry2.getTargetStr(), startsWith("isisauditdemo.SomeAuditedObject:"));
        assertThat(auditEntry2.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject#number"));
        assertThat(auditEntry2.getPropertyId(), is("number"));
        assertThat(auditEntry2.getPreValue(), is(nullValue()));
        assertThat(auditEntry2.getPostValue(), is("123"));
        assertThat(auditEntry2.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));
    }

    private static List<AuditEntry> sorted(List<AuditEntry> auditEntries) {
        Collections.sort(auditEntries, new Comparator<AuditEntry>() {
            @Override
            public int compare(AuditEntry o1, AuditEntry o2) {
                return o1.getMemberIdentifier().compareTo(o2.getMemberIdentifier());
            }
        });
        return auditEntries;
    }


}