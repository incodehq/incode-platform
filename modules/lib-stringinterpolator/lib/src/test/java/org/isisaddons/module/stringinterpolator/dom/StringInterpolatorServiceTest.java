package org.isisaddons.module.stringinterpolator.dom;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringInterpolatorServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    Map<String, String> properties;
    Customer customer;

    StringInterpolatorService service;

    public static class Interpolate extends StringInterpolatorServiceTest {

        @Before
        public void setUp() throws Exception {
            service = new StringInterpolatorService();
        }

        public static class WhenProperty extends Interpolate {

            @Before
            public void setUp() throws Exception {
                super.setUp();

                properties = ImmutableMap.of(
                        "isis.asf.website", "http://isis.apache.org",
                        "isis.asf.website.noScheme", "isis.apache.org",
                        "isis.asf.website.documentationPage", "documentation.html",
                        "isis.github.site", "http://github.com/isis/isis");

                service.init(properties);
            }

            @Test
            public void simple() throws Exception {
                String interpolated = service.interpolate(null, "${properties['isis.asf.website']}");
                assertThat(interpolated, is("http://isis.apache.org"));
            }

            @Test
            public void suffix() throws Exception {
                String interpolated = service.interpolate(null, "${properties['isis.asf.website']}/documentation.html");
                assertThat(interpolated, is("http://isis.apache.org/documentation.html"));
            }


            @Test
            public void prefix() throws Exception {
                String interpolated = service.interpolate(null, "http://${properties['isis.asf.website.noScheme']}");
                assertThat(interpolated, is("http://isis.apache.org"));
            }

            @Test
            public void multiple() throws Exception {
                String interpolated = service.interpolate(null, "${properties['isis.asf.website']}/${properties['isis.asf.website.documentationPage']}");
                assertThat(interpolated, is("http://isis.apache.org/documentation.html"));
            }

            @Test
            public void complex() throws Exception {
                String interpolated = service.interpolate(null, "http://${properties['isis.asf.website.noScheme']}/${properties['isis.asf.website.documentationPage']}#Core");
                assertThat(interpolated, is("http://isis.apache.org/documentation.html#Core"));
            }

        }

        public static class WhenRoot extends Interpolate {


            @Before
            public void setUp() throws Exception {
                super.setUp();

                customer = new Customer();
                customer.setFirstName("Fred");

                Address address = new Address();
                address.setHouseNumber(34);
                address.setPostalCode("AB12 34DF");
                customer.setAddress(address);

                service.init(properties);
            }

            @Test
            public void simple() throws Exception {
                final class CustomRoot extends StringInterpolatorService.Root {

                    private Customer customer;

                    public CustomRoot(Object context, Customer customer) {
                        super(context);
                        this.customer = customer;
                    }

                    public Customer getCustomer() {
                        return customer;
                    }
                }
                String interpolated = service.interpolate(new CustomRoot(null, customer), "${customer.firstName}");
                assertThat(interpolated, is("Fred"));
            }

        }

        public static class WhenThis extends Interpolate {

            @Rule
            public ExpectedException expectedException = ExpectedException.none();

            @Before
            public void setUp() throws Exception {
                super.setUp();

                customer = new Customer();
                customer.setFirstName("Fred");

                Address address = new Address();
                address.setHouseNumber(34);
                address.setPostalCode("AB12 34DF");
                customer.setAddress(address);

                service.init(properties);
            }

            @Test
            public void simple() throws Exception {
                String interpolated = service.interpolate(customer, "${this.firstName}");
                assertThat(interpolated, is("Fred"));
            }

            @Test
            public void nullField() throws Exception {
                String interpolated = service.interpolate(customer, "${this.lastName}");
                assertThat(interpolated, is(""));
            }

            @Test
            public void walkGraph() throws Exception {
                String interpolated = service.interpolate(customer, "${this.address.houseNumber}");
                assertThat(interpolated, is("34"));
            }

            @Test
            public void walkGraphToNull() throws Exception {
                String interpolated = service.interpolate(customer, "${this.address.town}");
                assertThat(interpolated, is(""));
            }

            @Test
            public void walkGraphThroughNull() throws Exception {
                String interpolated = service.interpolate(customer, "${this.billingAddress.town}");
                assertThat(interpolated, is("${this.billingAddress.town}"));
            }

            @Test
            public void walkGraphThroughNullStrictMode() throws Exception {
                expectedException.expectMessage("could not parse: this.billingAddress.town");
                service.withStrict(true).interpolate(customer, "${this.billingAddress.town}");
            }

            @Test
            public void conditionals() throws Exception {
                String interpolated = service.interpolate(customer,
                        "${this.firstName}"
                                + "${this.lastName != null? this.lastName : ''}"
                                + "${this.address != null? ' lives at ' + this.address.houseNumber + ', ' + this.address.postalCode: ''}"
                                + "${this.billingAddress != null? ' , bill to ' + this.billingAddress.postTown : ''}");
                assertThat(interpolated, is("Fred lives at 34, AB12 34DF"));
            }
        }

    }

}
