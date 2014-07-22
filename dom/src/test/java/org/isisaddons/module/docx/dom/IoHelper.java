/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.docx.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;

public class IoHelper {

    private final Class<?> baseClass;

    public IoHelper(final Object base) {
        this.baseClass = base.getClass();
    }

    public byte[] asBytes(String fileName) throws IOException {
        final ByteArrayOutputStream baos2 = asBaos(fileName);
        return baos2.toByteArray();
    }

    public ByteArrayOutputStream asBaos(String fileName) throws IOException {
        final ByteArrayInputStream bais = asBais(fileName);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteStreams.copy(bais, baos);
        return baos;
    }

    public String asString(final String fileName) throws IOException {
        final URL fileUrl = asUrl(fileName);
        return Resources.toString(fileUrl, Charset.forName("UTF-8"));
    }

    public ByteArrayInputStream asBais(final String fileName) throws IOException {
        final URL fileUrl = asUrl(fileName);
        return new ByteArrayInputStream(Resources.toByteArray(fileUrl));
    }

    public URL asUrl(final String fileName) {
        return Resources.getResource(baseClass, fileName);
    }

    public File asFileInSameDir(final String existingFile, String newFile) {
        final File dir = toFile(asUrl(existingFile)).getParentFile();
        return new File(dir, newFile);
    }

    static File toFile(URL url) {
        File file;
        String path;

        try {
            path = url.toURI().getSchemeSpecificPart();
            if ((file = new File(path)).exists()) return file;
        } catch (URISyntaxException e) {
        }

        try {
            path = url.toExternalForm();
            if (path.startsWith("file:")) path = path.substring("file:".length());
            if ((file = new File(path)).exists()) return file;

        } catch (Exception e) {
        }

        return null;
    }

}
