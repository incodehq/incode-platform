/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.audit.integtests;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import org.isisaddons.module.audit.fixture.dom.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.dom.SomeAuditedObjects;
import org.isisaddons.module.audit.fixture.scripts.SomeAuditedObjectsFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.applib.services.audit.AuditingService3;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.apache.isis.objectstore.jdo.applib.service.audit.AuditEntryJdo;
import org.apache.isis.objectstore.jdo.applib.service.audit.AuditingServiceJdoContributions;
import org.apache.isis.objectstore.jdo.applib.service.audit.AuditingServiceJdoRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SomeAuditedObjectTest extends AuditModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SomeAuditedObjectsFixture());
    }

    @Inject
    private SomeAuditedObjects someAuditedObjects;

    @Inject
    private IsisJdoSupport isisJdoSupport;

    @Inject
    private AuditingService3 auditingService3;

    @Inject
    private AuditingServiceJdoRepository auditingServiceJdoRepository;

    @Inject
    private AuditingServiceJdoContributions auditingServiceJdoContributions;

    @Inject
    private BookmarkService bookmarkService;

    @Test
    public void auditEntriesCreatedOnCommit() throws Exception {

        // given
        isisJdoSupport.executeUpdate("delete from \"IsisAuditEntry\"");
        container().flush();

        // when
        wrap(someAuditedObjects).create("Faz");

        // currently necessary to ensure that created objects are picked up and enlisted in the transaction as newly created
        container().flush();

        // then
        final List<Map<String, Object>> prior = isisJdoSupport.executeSql("SELECT * FROM \"IsisAuditEntry\"");
        Assert.assertThat(prior.size(), is(0));

        // when
        this.nextTransaction();

        // then
        final List<Map<String, Object>> after = isisJdoSupport.executeSql("SELECT * FROM \"IsisAuditEntry\"");
        assertThat(after.size(), is(2));
    }

    @Test
    public void create() throws Exception {

        // given
        isisJdoSupport.executeUpdate("delete from \"IsisAuditEntry\"");
        container().flush();

        // when
        final SomeAuditedObject newObject = wrap(someAuditedObjects).create("Faz");

        // currently necessary to ensure that created objects are picked up and enlisted in the transaction as newly created
        container().flush();

        this.nextTransaction();

        // then
        final Bookmark bookmark = bookmarkService.bookmarkFor(newObject);

        final List<Map<String, Object>> after = isisJdoSupport.executeSql("SELECT * FROM \"IsisAuditEntry\"");
        assertThat(after.size(), is(2));

        final List<AuditEntryJdo> auditEntries = auditingServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
        assertThat(auditEntries.size(), is(2));

        final AuditEntryJdo auditEntry1 = auditEntries.get(1);

        final Timestamp timestamp = auditEntry1.getTimestamp();
        final UUID transactionId = auditEntry1.getTransactionId();

        assertThat(auditEntry1.getUser(), is("tester"));
        assertThat(timestamp, is(notNullValue()));
        assertThat(transactionId, is(notNullValue()));
        assertThat(auditEntry1.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry1.getTargetStr(), is("SOME_AUDITED_OBJECT:L_3"));
        assertThat(auditEntry1.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.SomeAuditedObject#name"));
        assertThat(auditEntry1.getPropertyId(), is("name"));
        assertThat(auditEntry1.getPreValue(), is("[NEW]"));
        assertThat(auditEntry1.getPostValue(), is("Faz"));
        assertThat(auditEntry1.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));

        final AuditEntryJdo auditEntry2 = auditEntries.get(0);

        assertThat(auditEntry2.getUser(), is("tester"));
        final Timestamp timestamp2 = auditEntry2.getTimestamp();
        assertThat(timestamp2, is(timestamp));
        final UUID transactionId2 = auditEntry1.getTransactionId();
        assertThat(transactionId2, is(transactionId));
        assertThat(auditEntry2.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry2.getTargetStr(), is("SOME_AUDITED_OBJECT:L_3"));
        assertThat(auditEntry2.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.SomeAuditedObject#number"));
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

        isisJdoSupport.executeUpdate("delete from \"IsisAuditEntry\"");
        container().flush();

        // when
        someAuditedObject.setName("Bob");
        someAuditedObject.setNumber(123);

        this.nextTransaction();

        // then

        final List<AuditEntryJdo> auditEntries = auditingServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
        assertThat(auditEntries.size(), is(2));

        final AuditEntryJdo auditEntry1 = auditEntries.get(1);

        final Timestamp timestamp = auditEntry1.getTimestamp();
        final UUID transactionId = auditEntry1.getTransactionId();

        assertThat(auditEntry1.getUser(), is("tester"));
        assertThat(timestamp, is(notNullValue()));
        assertThat(transactionId, is(notNullValue()));
        assertThat(auditEntry1.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry1.getTargetStr(), startsWith("SOME_AUDITED_OBJECT:L_"));
        assertThat(auditEntry1.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.SomeAuditedObject#name"));
        assertThat(auditEntry1.getPropertyId(), is("name"));
        assertThat(auditEntry1.getPreValue(), is("Foo"));
        assertThat(auditEntry1.getPostValue(), is("Bob"));
        assertThat(auditEntry1.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));

        final AuditEntryJdo auditEntry2 = auditEntries.get(0);

        assertThat(auditEntry2.getUser(), is("tester"));
        final Timestamp timestamp2 = auditEntry2.getTimestamp();
        assertThat(timestamp2, is(timestamp));
        final UUID transactionId2 = auditEntry1.getTransactionId();
        assertThat(transactionId2, is(transactionId));
        assertThat(auditEntry2.getTargetClass(), is("Some Audited Object"));
        assertThat(auditEntry2.getTargetStr(), startsWith("SOME_AUDITED_OBJECT:L_"));
        assertThat(auditEntry2.getMemberIdentifier(), is("org.isisaddons.module.audit.fixture.dom.SomeAuditedObject#number"));
        assertThat(auditEntry2.getPropertyId(), is("number"));
        assertThat(auditEntry2.getPreValue(), is(nullValue()));
        assertThat(auditEntry2.getPostValue(), is("123"));
        assertThat(auditEntry2.getType(), is(DomainChangeJdoAbstract.ChangeType.AUDIT_ENTRY));
    }

}