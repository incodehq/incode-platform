package org.incode.module.minio.dopclient;

import java.util.List;

import org.incode.module.minio.common.DomainObjectPropertyValue;
import org.incode.module.minio.dopclient.DomainObjectPropertyClient;

public class DomainObjectPropertyClient_findToArchive_Main {

    public static void main(String[] args) {
        final DomainObjectPropertyClient domainObjectPropertyClient = new DomainObjectPropertyClient();
        domainObjectPropertyClient.setBase("http://localhost:8080/restful/");
        domainObjectPropertyClient.setUsername("estatio-admin");
        domainObjectPropertyClient.setPassword("pass");
        domainObjectPropertyClient.init();

        final List<DomainObjectPropertyValue> toArchive = domainObjectPropertyClient.findToArchive("test");
        for (final DomainObjectPropertyValue docBlob : toArchive) {
            System.out.println(docBlob.toString());
        }
    }

}
