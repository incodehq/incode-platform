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
package org.isisaddons.module.publishmq.dom.jdo.status;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY
)
public class StatusMessageService {

    /**
     * Used within the URL (rather than the fully-qualified class name).
     * @return
     */
    public String id() {
        return "StatusMessageService";
    }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            command = CommandReification.DISABLED,
            publishing = Publishing.DISABLED
    )
    public void log(
            @ParameterLayout(named = "transactionId")
            final String transactionId,
            @ParameterLayout(named = "message")
            final String message,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "oid")
            final String oid,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "uri")
            final String uri,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "status")
            final Integer status,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "detail")
            final String detail) {

        final StatusMessage statusMessage = repositoryService.instantiate(StatusMessage.class);

        statusMessage.setTimestamp(clockService.nowAsJavaSqlTimestamp());
        statusMessage.setTransactionId(UUID.fromString(transactionId));
        statusMessage.setMessage(message);
        statusMessage.setOid(oid);
        statusMessage.setUri(uri);
        statusMessage.setStatus(status);
        statusMessage.setDetail(detail);

        repositoryService.persist(statusMessage);
    }

    //region > helpers

    @Inject
    RepositoryService repositoryService;

    @Inject
    ClockService clockService;

}
