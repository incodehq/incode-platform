package org.isisaddons.module.publishmq.externalsystemadapter.wsdl;

import java.net.URL;

/**
 * For locating the WSDL resources.
 */
public class ExternalSystemWsdl {

    public static final String WSDL = "org/isisaddons/module/publishmq/externalsystemadapter/wsdl/DemoObject.wsdl";

    private ExternalSystemWsdl(){}

    public static URL getWsdl() {
        return ExternalSystemWsdl.class.getClassLoader().getResource(WSDL);
    }
}
