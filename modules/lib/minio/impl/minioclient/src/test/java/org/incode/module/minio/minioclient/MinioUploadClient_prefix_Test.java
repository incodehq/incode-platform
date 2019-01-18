package org.incode.module.minio.minioclient;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinioUploadClient_prefix_Test {

    @Test
    public void no_prefixes() throws Exception {
        assertThat(MinioUploadClient.prefix("myApp", "xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
    }

    @Test
    public void when_bucket_prefix() throws Exception {
        assertThat(MinioUploadClient.prefix("myApp", "myApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "myApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "MyApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "MyApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "myApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "myApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "MyApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "MyApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
    }

    @Test
    public void when_amz_bucket_prefix() throws Exception {
        assertThat(MinioUploadClient.prefix("myApp", "X-AMZ-Meta-myApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "X-AMZ-Meta-myApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "X-AMZ-Meta-MyApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("myApp", "X-AMZ-Meta-MyApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "X-AMZ-Meta-myApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "X-AMZ-Meta-myApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "X-AMZ-Meta-MyApp-Xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
        assertThat(MinioUploadClient.prefix("MyApp", "X-AMZ-Meta-MyApp-xxx")).isEqualTo("X-Amz-Meta-MyApp-Xxx");
    }
}