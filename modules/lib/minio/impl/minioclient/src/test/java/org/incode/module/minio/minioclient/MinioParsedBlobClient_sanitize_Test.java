package org.incode.module.minio.minioclient;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * as per https://docs.aws.amazon.com/AmazonS3/latest/dev/UsingMetadata.html
 */
public class MinioParsedBlobClient_sanitize_Test {

    @Test
    public void replaces_with_underscores() throws Exception {
        assertSanitized("a&b", "a_b");
        assertSanitized("a@b", "a_b");
        assertSanitized("a:b", "a_b");
        assertSanitized("a,b", "a_b");
        assertSanitized("a+b", "a_b");
        assertSanitized("a$b", "a_b");
        assertSanitized("a=b", "a_b");
        assertSanitized("a+b", "a_b");
        assertSanitized("a?b", "a_b");
        assertSanitized("a;b", "a_b");

        assertSanitized("a&b c@d e:f g,h i$j k=l m+n p?q r;s", "a_b c_d e_f g_h i_j k_l m_n p_q r_s");
    }

    @Test
    public void spaces() throws Exception {
        assertSanitized("a  b", "a b");
        assertSanitized("a   b", "a b");

        assertSanitized("a  b c   d", "a b c d");
    }

    @Test
    public void stripped() throws Exception {
        assertSanitized("a\\b", "ab");
        assertSanitized("a^b", "ab");
        assertSanitized("a`b", "ab");
        assertSanitized("a>b", "ab");
        assertSanitized("a<b", "ab");
        assertSanitized("a{b", "ab");
        assertSanitized("a}b", "ab");
        assertSanitized("a[b", "ab");
        assertSanitized("a]b", "ab");
        assertSanitized("a#b", "ab");
        assertSanitized("a%b", "ab");
        assertSanitized("a\"b", "ab");
        assertSanitized("a~b", "ab");
        assertSanitized("a|b", "ab");

        assertSanitized("a\\b c^d e`f g<h i>j k{l m}n o[p q]r s#t u%v w\"x y~z a|b", "ab cd ef gh ij kl mn op qr st uv wx yz ab");
    }

    void assertSanitized(final String fileName, final String expected) {
        assertThat(MinioUploadClient.sanitize(fileName)).isEqualTo(expected);
    }

}