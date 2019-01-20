package org.incode.module.minio.dopclient.archive;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class ArchivedArgs_asJson_Test {

    @Test
    public void xxx() throws Exception {
        final ArchivedArgs archivedArgs = new ArchivedArgs();
        archivedArgs.setSourceBookmark(new StringValue("incode.Document:123"));
        archivedArgs.setSourceProperty(new StringValue("blob"));
        archivedArgs.setExternalUrl(new StringValue("http://yadayada"));

        final String s = archivedArgs.asJson();

        Assert.assertThat(s, is(not(nullValue())));
    }

}