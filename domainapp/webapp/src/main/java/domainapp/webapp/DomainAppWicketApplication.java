package domainapp.webapp;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.google.inject.util.Providers;

import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;

import org.apache.isis.viewer.wicket.viewer.IsisWicketApplication;
import org.apache.isis.viewer.wicket.viewer.integration.wicket.AuthenticatedWebSessionForIsis;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;

/**
 * As specified in <tt>web.xml</tt>.
 * 
 * <p>
 * See:
 * <pre>
 * &lt;filter>
 *   &lt;filter-name>wicket&lt;/filter-name>
 *    &lt;filter-class>org.apache.wicket.protocol.http.WicketFilter&lt;/filter-class>
 *    &lt;init-param>
 *      &lt;param-name>applicationClassName&lt;/param-name>
 *      &lt;param-value>DemoApplication&lt;/param-value>
 *    &lt;/init-param>
 * &lt;/filter>
 * </pre>
 * 
 */
public class DomainAppWicketApplication extends IsisWicketApplication {

    private static final long serialVersionUID = 1L;

    @Override
    protected void init() {
        super.init();

        IBootstrapSettings settings = Bootstrap.getSettings();
        settings.setThemeProvider(new BootswatchThemeProvider(BootswatchTheme.Flatly));
    }


    /**
     * uncomment for a (slightly hacky) way of allowing logins using query args, eg:
     *
     * <tt>?user=sven&pass=pass</tt>
     *
     * <p>
     * for demos only, obvious.
     */
    private final static boolean DEMO_MODE_USING_CREDENTIALS_AS_QUERYARGS = false;

    @Override
    @SuppressWarnings("UnusedAssignment")
    public Session newSession(final Request request, final Response response) {
        if(!DEMO_MODE_USING_CREDENTIALS_AS_QUERYARGS) {
            return super.newSession(request, response);
        }

        // else demo mode
        final AuthenticatedWebSessionForIsis sessionForIsis =
                (AuthenticatedWebSessionForIsis) super.newSession(request, response);
        final org.apache.wicket.util.string.StringValue user =
                request.getRequestParameters().getParameterValue("user");
        final org.apache.wicket.util.string.StringValue password =
                request.getRequestParameters().getParameterValue("pass");
        sessionForIsis.signIn(user.toString(), password.toString());
        return sessionForIsis;
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public WebRequest newWebRequest(HttpServletRequest servletRequest, String filterPath) {
        if(!DEMO_MODE_USING_CREDENTIALS_AS_QUERYARGS) {
            return super.newWebRequest(servletRequest, filterPath);
        }

        // else demo mode
        try {
            String user = servletRequest.getParameter("user");
            if (user != null) {
                servletRequest.getSession().invalidate();
            }
        } catch (Exception e) {
            // ignore
        }
        WebRequest request = super.newWebRequest(servletRequest, filterPath);
        return request;
    }

    private static final String APP_NAME = "Incode App";

    @Override
    protected Module newIsisWicketModule() {
        final Module isisDefaults = super.newIsisWicketModule();
        
        final Module overrides = new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named("applicationName")).toInstance(APP_NAME);
                bind(String.class).annotatedWith(Names.named("applicationCss")).toInstance("css/application.css");
                bind(String.class).annotatedWith(Names.named("applicationJs")).toInstance("scripts/application.js");
                bind(String.class).annotatedWith(Names.named("welcomeMessage")).toInstance(readLines(getClass(), "welcome.html", APP_NAME));
                bind(String.class).annotatedWith(Names.named("aboutMessage")).toInstance(APP_NAME);
                bind(InputStream.class).annotatedWith(Names.named("metaInfManifest")).toProvider(
                        Providers.of(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF")));
            }
        };

        return Modules.override(isisDefaults).with(overrides);
    }

}
