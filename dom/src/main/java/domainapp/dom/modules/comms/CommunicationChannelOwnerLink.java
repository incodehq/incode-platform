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
package domainapp.dom.modules.comms;

import domainapp.dom.modules.poly.SubjectPolymorphicReferenceLink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.DomainObject;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findBySubject", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE subject == :subject"),
        @javax.jdo.annotations.Query(
                name = "findByPolymorphicReference", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE polymorphicReferenceObjectType == :polymorphicReferenceObjectType "
                        + "   && polymorphicReferenceIdentifier == :polymorphicReferenceIdentifier ")
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_source_destination_UNQ", members = {"source,destinationObjectType,destinationIdentifier"})
@DomainObject(
        objectType = "comms.CommunicationChannelOwnerLink"
)
public abstract class CommunicationChannelOwnerLink extends SubjectPolymorphicReferenceLink<CommunicationChannel, CommunicationChannelOwner, CommunicationChannelOwnerLink> {

    //region > constructor
    public CommunicationChannelOwnerLink() {
        super("{polymorphicReference} owns {subject}");
    }
    //endregion

    //region > subject (property)
    @Override
    public CommunicationChannel getSubjectObj() {
        return getSubject();
    }

    @Override
    public void setSubjectObj(final CommunicationChannel subjectObj) {
        setSubject(subjectObj);
    }

    private CommunicationChannel subject;
    @Column(
            allowsNull = "false",
            name = "communicationChannel_id"
    )
    public CommunicationChannel getSubject() {
        return subject;
    }

    public void setSubject(final CommunicationChannel subject) {
        this.subject = subject;
    }
    //endregion

    //region > polymorphicReferenceObjectType (property)
    private String polymorphicReferenceObjectType;

    @Column(allowsNull = "false", length = 255)
    public String getPolymorphicReferenceObjectType() {
        return polymorphicReferenceObjectType;
    }

    public void setPolymorphicReferenceObjectType(final String polymorphicReferenceObjectType) {
        this.polymorphicReferenceObjectType = polymorphicReferenceObjectType;
    }

    //endregion

    //region > polymorphicReferenceIdentifier (property)
    private String polymorphicReferenceIdentifier;

    @Column(allowsNull = "false", length = 255)
    public String getPolymorphicReferenceIdentifier() {
        return polymorphicReferenceIdentifier;
    }

    public void setPolymorphicReferenceIdentifier(final String polymorphicReferenceIdentifier) {
        this.polymorphicReferenceIdentifier = polymorphicReferenceIdentifier;
    }
    //endregion

}
