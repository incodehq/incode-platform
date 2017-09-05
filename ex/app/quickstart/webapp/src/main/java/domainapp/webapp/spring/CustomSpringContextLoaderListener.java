package domainapp.webapp.spring;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * When prototyping, can skip the spring bootstrapping by running with -DskipSpring system property.
 */
public class CustomSpringContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        final boolean skipSpring = skipSpring();
        if(skipSpring) {
            return;
        }
        super.contextInitialized(event);
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        final boolean skipSpring = skipSpring();
        if(skipSpring) {
            return;
        }
        super.contextDestroyed(event);
    }

    protected boolean skipSpring() {
        final String skipSpringStr = System.getProperty("skipSpring");
        return skipSpringStr != null;
    }


}
