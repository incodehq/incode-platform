package org.incode.module.minio.dopclient;

import org.incode.module.minio.common.DomainObjectPropertyValue;

public class DomainObjectPropertyClient_readSingle_Main {

    public static void main(String[] args) {
        final DomainObjectPropertyClient domainObjectPropertyClient = new DomainObjectPropertyClient();
        domainObjectPropertyClient.setBase("http://localhost:8080/restful/");
        domainObjectPropertyClient.setUsername("estatio-admin");
        domainObjectPropertyClient.setPassword("pass");
        domainObjectPropertyClient.init();

        final DomainObjectPropertyValue dopv = domainObjectPropertyClient.readSingle("test", "incodedocuments.Document:178", "blob");

        System.out.println(dopv);
    }

}
