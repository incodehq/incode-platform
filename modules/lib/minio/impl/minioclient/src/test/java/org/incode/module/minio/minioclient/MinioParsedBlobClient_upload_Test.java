package org.incode.module.minio.minioclient;

import java.net.URL;
import java.util.Map;

import org.junit.Test;

import lombok.SneakyThrows;
import static org.assertj.core.api.Assertions.assertThat;

public class MinioParsedBlobClient_upload_Test {

    @Test
    public void retries_uploads_with_no_metadata() throws Exception {

        // given
        final MinioUploadClient client = new MinioUploadClient() {
            @SneakyThrows
            @Override
            URL upload(
                    final String objectName,
                    final String property,
                    final String contentType,
                    final byte[] bytes,
                    final Map<String, String> metadata) {
                if(metadata.size() > 0) {
                    throw new Exception("bad metadata");
                }
                return new URL("http://yadayada.com");
            }
        };

        // when
        final URL upload = client.upload(null, null, "ab^&  c", null, (byte[])null);

        // then
        assertThat(upload).isNotNull();
    }

}