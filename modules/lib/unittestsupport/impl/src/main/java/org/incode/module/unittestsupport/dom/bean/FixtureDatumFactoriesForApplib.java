package org.incode.module.unittestsupport.dom.bean;

import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.applib.value.Date;

public class FixtureDatumFactoriesForApplib {

	public static PojoTester.FixtureDatumFactory<Date> dates() {
		return new PojoTester.FixtureDatumFactory<>(Date.class, new Date(2012, 7, 19), new Date(2012, 7, 20), new Date(2012, 8, 19), new Date(2013, 7, 19));
	}

	public static PojoTester.FixtureDatumFactory<Blob> blobs() {
		return new PojoTester.FixtureDatumFactory<>(Blob.class,
				new Blob("foo", "application/pdf", new byte[]{1,2,3}),
				new Blob("bar", "application/docx", new byte[]{4,5}),
				new Blob("baz", "application/xlsx", new byte[]{7,8,9,0})
				);
	}

	public static PojoTester.FixtureDatumFactory<Clob> clobs() {
		return new PojoTester.FixtureDatumFactory<>(Clob.class,
				new Clob("foo", "text/html", "<html/>".toCharArray()),
				new Clob("bar", "text/plain", "hello world".toCharArray()),
				new Clob("baz", "text/ini", "foo=bar".toCharArray())
				);
	}


}
