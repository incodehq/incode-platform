package org.incode.module.minio.common;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import lombok.Data;

@Data
class ParsedClob {

    final String fileName;
    final String contentType;
    final String chars;

    static ParsedClob from(String encoded) {
        final Iterable<String> split = Splitter.on(':').split(encoded);
        final String[] parts = Iterables.toArray(split, String.class);
        return new ParsedClob(parts[0],parts[1], parts[2]);
    }
    public String asEncodedString() {
        return fileName + ":" + contentType + ":" + chars;
    }
}
