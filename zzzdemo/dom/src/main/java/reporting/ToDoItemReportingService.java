package reporting;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.danhaywood.isis.domainservice.stringinterpolator.StringInterpolatorService;

import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;

import dom.todo.ToDoItem;

public class ToDoItemReportingService {

    public ToDoItemReportingService() throws IOException {
    }

    @NotInServiceMenu
    @NotContributed(As.ASSOCIATION) // ie contributed as action
    public URL open(ToDoItem toDoItem) throws MalformedURLException {
        String urlStr = stringInterpolationService.interpolate(toDoItem, "${properties['isis.website']}/${this.documentationPage}");
        return new URL(urlStr);
    }

    // //////////////////////////////////////
    
    @javax.inject.Inject
    private StringInterpolatorService stringInterpolationService;
}

