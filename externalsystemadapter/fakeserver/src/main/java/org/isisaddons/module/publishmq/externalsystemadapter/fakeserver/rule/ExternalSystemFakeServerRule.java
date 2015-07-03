
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.isisaddons.module.publishmq.externalsystemadapter.fakeserver.rule;

import org.apache.isis.core.unittestsupport.soap.SoapEndpointPublishingRule;

import org.isisaddons.module.publishmq.externalsystemadapter.fakeserver.ExternalSystemFakeServer;

public class ExternalSystemFakeServerRule extends SoapEndpointPublishingRule<ExternalSystemFakeServer> {

    public ExternalSystemFakeServerRule() {
        this(54345);
    }
    public ExternalSystemFakeServerRule(final int port) {
        super(buildAddress(port), () -> new ExternalSystemFakeServer());
    }

    protected static String buildAddress(final int port) {
        return String.format("http://localhost:%d/any/old/string/will/work", port);
    }

}
