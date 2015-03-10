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
package domainapp.fixture.dom.modules.comms;

import org.isisaddons.module.poly.dom.SubjectPolymorphicReferenceLink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import com.google.common.base.Function;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Programmatic;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCommunicationChannel", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.fixture.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE communicationChannel == :communicationChannel"),
        @javax.jdo.annotations.Query(
                name = "findByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.fixture.dom.modules.comms.CommunicationChannelOwnerLink "
                        + "WHERE ownerObjectType == :ownerObjectType "
                        + "   && ownerIdentifier == :ownerIdentifier ")
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_commchannel_owner_UNQ", members = {"communicationChannel,ownerObjectType,ownerIdentifier"})
@DomainObject(
        objectType = "comms.CommunicationChannelOwnerLink"
)
public abstract class CommunicationChannelOwnerLink extends SubjectPolymorphicReferenceLink<CommunicationChannel, CommunicationChannelOwner, CommunicationChannelOwnerLink> {

    //region > constructor
    public CommunicationChannelOwnerLink() {
        super("{polymorphicReference} owns {subject}");
    }
    //endregion

    //region > SubjectPolymorphicReferenceLink API
    @Override
    @Programmatic
    public CommunicationChannel getSubject() {
        return getCommunicationChannel();
    }

    @Override
    @Programmatic
    public void setSubject(final CommunicationChannel subject) {
        setCommunicationChannel(subject);
    }

    @Override
    @Programmatic
    public String getPolymorphicReferenceObjectType() {
        return getOwnerObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicReferenceObjectType(final String polymorphicReferenceObjectType) {
        setOwnerObjectType(polymorphicReferenceObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicReferenceIdentifier() {
        return getOwnerIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicReferenceIdentifier(final String polymorphicReferenceIdentifier) {
        setOwnerIdentifier(polymorphicReferenceIdentifier);
    }
    //endregion

    //region > communicationChannel (property)
    private CommunicationChannel communicationChannel;
    @Column(
            allowsNull = "false",
            name = "communicationChannel_id"
    )
    public CommunicationChannel getCommunicationChannel() {
        return communicationChannel;
    }

    public void setCommunicationChannel(final CommunicationChannel communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    //endregion

    //region > ownerObjectType (property)
    private String ownerObjectType;

    @Column(allowsNull = "false", length = 255)
    public String getOwnerObjectType() {
        return ownerObjectType;
    }

    public void setOwnerObjectType(final String ownerObjectType) {
        this.ownerObjectType = ownerObjectType;
    }
    //endregion

    //region > ownerIdentifier (property)
    private String ownerIdentifier;

    @Column(allowsNull = "false", length = 255)
    public String getOwnerIdentifier() {
        return ownerIdentifier;
    }

    public void setOwnerIdentifier(final String ownerIdentifier) {
        this.ownerIdentifier = ownerIdentifier;
    }
    //endregion

    public static class Functions {
        public static Function<CommunicationChannelOwnerLink, CommunicationChannel> GET_COMMUNICATION_CHANNEL = new Function<CommunicationChannelOwnerLink, CommunicationChannel>() {
            @Override
            public CommunicationChannel apply(final CommunicationChannelOwnerLink input) {
                return input.getCommunicationChannel();
            }
        };

    }

}
