package org.incode.example.note;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.note.fixture.NoteModule_teardown;

@XmlRootElement(name = "module")
public class NoteModule extends ModuleAbstract {

    //region > constants

    public static class JdoColumnLength {

        private JdoColumnLength(){}

        public static final int NOTES = 4000;
        public static final int CALENDAR_NAME = 254;

        public static final int BOOKMARK = 2000;
    }

    public static class MultiLine {

        private MultiLine(){}

        public static final int NOTES = 20;
    }

    //endregion

    //region > ui event classes
    public abstract static class TitleUiEvent<S>
            extends org.apache.isis.applib.services.eventbus.TitleUiEvent<S> { }
    public abstract static class IconUiEvent<S>
            extends org.apache.isis.applib.services.eventbus.IconUiEvent<S> { }
    public abstract static class CssClassUiEvent<S>
            extends org.apache.isis.applib.services.eventbus.CssClassUiEvent<S> { }
    //endregion

    //region > event classes
    public abstract static class ActionDomainEvent<S>
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> { }
    public abstract static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> { }
    public abstract static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> { }
    //endregion

    @Override public FixtureScript getTeardownFixture() {
        return new NoteModule_teardown();
    }

}
