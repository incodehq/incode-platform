package org.incode.module.minio.dopclient;

import org.incode.module.minio.common.DomainObjectPropertyValue;
import org.incode.module.minio.dopclient.DomainObjectPropertyClient;

public class DomainObjectPropertyClient_archive_Main {

    public static void main(String[] args) {
        final DomainObjectPropertyClient domainObjectPropertyClient = new DomainObjectPropertyClient();
        domainObjectPropertyClient.setBase("http://localhost:8080/restful/");
        domainObjectPropertyClient.setUsername("estatio-admin");
        domainObjectPropertyClient.setPassword("pass");
        domainObjectPropertyClient.init();

        final DomainObjectPropertyValue domainObjectPropertyValue = new DomainObjectPropertyValue();
        domainObjectPropertyValue.setSourceBookmark("incodedocuments.Document:27");
        domainObjectPropertyValue.setSourceProperty("blob");
        domainObjectPropertyClient
                .archive(domainObjectPropertyValue, "http://minio.int.prd.ecpnv.com/estatio-test/db/incodeDocument.Document/5");
    }

}
