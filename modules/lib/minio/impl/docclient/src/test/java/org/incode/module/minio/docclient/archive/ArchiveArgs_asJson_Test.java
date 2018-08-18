package org.incode.module.minio.docclient.archive;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class ArchiveArgs_asJson_Test {

    @Test
    public void xxx() throws Exception {
        final ArchiveArgs archiveArgs = new ArchiveArgs();
        archiveArgs.setDocBookmark(new StringValue("incode.Document:123"));
        archiveArgs.setExternalUrl(new StringValue("http://yadayada"));

        final String s = archiveArgs.asJson();

        Assert.assertThat(s, is(not(nullValue())));
    }

}