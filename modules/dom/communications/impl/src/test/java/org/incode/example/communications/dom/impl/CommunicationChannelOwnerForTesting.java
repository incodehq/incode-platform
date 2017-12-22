package org.incode.example.communications.dom.impl;

import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.security.dom.tenancy.HasAtPath;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner;

public class CommunicationChannelOwnerForTesting implements CommunicationChannelOwner, HasAtPath {

    @Programmatic
    @Override
    public String getAtPath() {
        return null;
    }

}
