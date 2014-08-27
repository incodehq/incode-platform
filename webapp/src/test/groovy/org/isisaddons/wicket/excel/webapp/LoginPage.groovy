package org.isisaddons.wicket.excel.webapp
import geb.Page

class LoginPage extends Page {

    static url = "/wicket"

    static at = {
        signInPanel != null
    }

    static content = {
        signInPanel { $("div.wicketSignInPanel") }

        loginForm { $("form#signInForm1" ) }
        loginFormSubmit { loginForm.find("input", type: "submit") }
    }

    void login(username, password) {
        loginForm.username = username
        loginForm.password = password
        loginFormSubmit.click(HomePage)
    }
}
