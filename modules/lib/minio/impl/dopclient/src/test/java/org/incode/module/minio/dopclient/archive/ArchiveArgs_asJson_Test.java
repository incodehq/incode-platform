package org.incode.module.minio.dopclient.archive;

import org.junit.Assert;
import org.junit.Test;

import org.incode.module.minio.dopclient.archive.ArchiveArgs;
import org.incode.module.minio.dopclient.archive.StringValue;

import static org.hamcrest.CoreMatchers.*;

public class ArchiveArgs_asJson_Test {

    @Test
    public void xxx() throws Exception {
        final ArchiveArgs archiveArgs = new ArchiveArgs();
        archiveArgs.setSourceBookmark(new StringValue("incode.Document:123"));
        archiveArgs.setSourceProperty(new StringValue("blob"));
        archiveArgs.setExternalUrl(new StringValue("http://yadayada"));

        final String s = archiveArgs.asJson();

        Assert.assertThat(s, is(not(nullValue())));
    }

}