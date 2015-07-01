package org.isisaddons.module.publishmq.externalsystemadapter.wsdl;

import java.net.URL;

/**
 * For locating the WSDL resources.
 */
public class ExternalSystemWsdl {

    public static final String DEMO_OBJECT_WSDL = "org/isisaddons/module/publishmq/externalsystemadapter/wsdl/DemoObject.wsdl";

    private ExternalSystemWsdl(){}

    public static URL getDemoObjectWsdl() {
        return ExternalSystemWsdl.class.getClassLoader().getResource(DEMO_OBJECT_WSDL);
    }
}
