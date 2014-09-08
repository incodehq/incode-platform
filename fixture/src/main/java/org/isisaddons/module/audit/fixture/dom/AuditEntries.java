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
package org.isisaddons.module.audit.fixture.dom;

import java.util.List;
import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;
import org.joda.time.LocalDate;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

@DomainService(menuOrder = "20")
public class AuditEntries {

    @ActionSemantics(ActionSemantics.Of.SAFE)
    public List<AuditEntry> listAuditEntries(
            final @Named("From") @Optional LocalDate from,
            final @Named("To") @Optional LocalDate to) {
        return auditingServiceRepository.findByFromAndTo(from, to);
    }

    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;

}
