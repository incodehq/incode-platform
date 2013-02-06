package com.danhaywood.isis.wicket.gmap3.service;

import java.io.StringReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.danhaywood.isis.wicket.gmap3.applib.Locatable;
import com.danhaywood.isis.wicket.gmap3.applib.Location;

@Hidden
public class LocationLookupService {

	// Greenwich Royal Observatory (unused)
	private static final Location DEFAULT_VALUE = new Location(51.4777479, 0.0d);
	
	private static final String BASEURL = "http://maps.googleapis.com/maps/api/geocode/";
	private static final String MODE = "xml";
	// is threadsafe
	private static final HttpClient httpclient = new HttpClient();

	public Location lookup(@Named("Description") String description) {

		final GetMethod get = new GetMethod(BASEURL + MODE);
        try {
        	final String query = URIUtil.encodeQuery("?address=" + description + "&sensor=false");
			get.setQueryString(query);
            
            httpclient.executeMethod(get);
            
            final String xml = get.getResponseBodyAsString();
            final SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new StringReader(xml));
            Element root = doc.getRootElement();
            String lat = root.getChild("result").getChild("geometry").getChild("location").getChildTextTrim("lat");
            String lon = root.getChild("result").getChild("geometry").getChild("location").getChildTextTrim("lng");
            return Location.fromString(lat + ";" + lon);
        } catch (Exception ex) {
            return null;
        }
	}
}
