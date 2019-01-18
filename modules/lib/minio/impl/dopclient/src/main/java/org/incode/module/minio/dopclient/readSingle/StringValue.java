package org.incode.module.minio.dopclient.readSingle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class StringValue {

    @Getter @Setter
    String sourceBookmark;

    @Getter @Setter
    String sourceProperty;

    @Override
    public String toString() {
        return String.valueOf(sourceBookmark) + "#" + String.valueOf(sourceProperty);
    }
}
