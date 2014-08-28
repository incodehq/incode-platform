package org.isisaddons.wicket.excel.webapp

import geb.junit4.GebReportingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GebPlaytimeTest extends GebReportingTest  {

    @Rule
    public IsisWebServerRule webServerRule = new IsisWebServerRule();

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void canLogin() throws Exception {
        to LoginPage
        report("at login page")

        screenshotRule.assertMatches(browser, "abc");

        login "sven", "pass"
        report("after login")


    }


}
