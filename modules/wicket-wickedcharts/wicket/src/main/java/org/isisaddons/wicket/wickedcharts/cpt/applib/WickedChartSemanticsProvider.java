package org.isisaddons.wicket.wickedcharts.cpt.applib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;
import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;

/**
 * For internal use; allows Isis to parse etc.
 */
public class WickedChartSemanticsProvider implements ValueSemanticsProvider<WickedChart> {

	public boolean isEqualByContent() {
		return true;
	}

	public boolean isImmutable() {
		return true;
	}

    public Parser<WickedChart> getParser() {
        return null;
    }

    public EncoderDecoder<WickedChart> getEncoderDecoder() {
        // TODO: reconstitute
        return new EncoderDecoder<WickedChart>() {

            public String toEncodedString(WickedChart toEncode) {
                return Base64Serializer.toString(toEncode);
            }

            public WickedChart fromEncodedString(String encodedString) {
                return (WickedChart) Base64Serializer.fromString(encodedString);
            }
        };
    }

    public DefaultsProvider<WickedChart> getDefaultsProvider() {
        return null;
    }

}

/**
 * 
 *copied from org.apache.isis.core:isis-core-metamodel
 */
class Base64Serializer {

    public static class Exception extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public Exception(java.lang.Exception e) {
            super(e);
        }
    }

    static Object fromString( String s ) {
        final byte [] data = Base64.decodeBase64( s );
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
            return ois.readObject();
        } catch (IOException e) {
            throw new Base64Serializer.Exception(e);
        } catch (ClassNotFoundException e) {
            throw new Base64Serializer.Exception(e);
        } finally {
            try {
                if(ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new Base64Serializer.Exception(e);
            }
        }
    }

    static String toString( Serializable serializable ) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream( baos );
            oos.writeObject( serializable );
            return new String( Base64.encodeBase64( baos.toByteArray() ) );
        } catch (IOException e) {
            throw new Base64Serializer.Exception(e);
        } finally {
            try {
                if(oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new Base64Serializer.Exception(e);
            }
        }
    }
}

