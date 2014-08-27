package org.isisaddons.wicket.excel.webapp
import geb.Page

class HomePage extends Page {

    static at = {
        aboutLink != null
    }

    static content = {
        aboutLink { $("a.about") }
    }
}