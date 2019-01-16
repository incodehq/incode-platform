package org.incode.module.minio.common;

import java.util.Base64;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import lombok.Data;

@Data
class ParsedBlob {
    final String fileName;
    final String contentType;
    final byte[] bytes;

    static ParsedBlob from(String encoded) {
        final Iterable<String> split = Splitter.on(':').split(encoded);
        final String[] parts = Iterables.toArray(split, String.class);
        final byte[] bytes = Base64.getDecoder().decode(parts[2]);
        return new ParsedBlob(parts[0],parts[1], bytes);
    }
    public String asEncodedString() {
        return fileName + ":" + contentType + ":" + Base64.getEncoder().encodeToString(bytes);
    }
}
