package org.incode.module.minio.miniodownloader;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinioDownloadClient_filenameFrom_Test {

    @Test
    public void no_content_disposition() throws Exception {
        final Map<String, List<String>> httpHeaders = Maps.newHashMap();

        final String s = MinioDownloadClient.filenameFrom(httpHeaders);

        assertThat(s).isNull();
    }

    @Test
    public void content_disposition_with_filename() throws Exception {
        final Map<String, List<String>> httpHeaders = Maps.newHashMap();
        final List<String> values = Lists.newArrayList();
        values.add("xxx;filename=\"abc.pdf\"");
        httpHeaders.put("Content-Disposition", values);

        final String s = MinioDownloadClient.filenameFrom(httpHeaders);

        assertThat(s).isEqualTo("abc.pdf");
    }

    @Test
    public void content_disposition_with_multiple_values_with_filenames() throws Exception {
        final Map<String, List<String>> httpHeaders = Maps.newHashMap();
        final List<String> values = Lists.newArrayList();
        values.add("xxx");
        values.add("yyy;filename=\"abc.pdf\"");
        values.add("zzz;filename=\"def.pdf\"");
        httpHeaders.put("Content-Disposition", values);

        final String s = MinioDownloadClient.filenameFrom(httpHeaders);

        assertThat(s).isEqualTo("abc.pdf");
    }

    @Test
    public void content_disposition_no_filename() throws Exception {
        final Map<String, List<String>> httpHeaders = Maps.newHashMap();
        final List<String> values = Lists.newArrayList();
        values.add("xxx;other=\"abc.pdf\"");
        httpHeaders.put("Content-Disposition", values);

        final String s = MinioDownloadClient.filenameFrom(httpHeaders);

        assertThat(s).isNull();
    }
}