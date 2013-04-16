package com.danhaywood.isis.wicket.ui.components.collectioncontents.excel;

import java.io.File;

import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.encoding.UrlEncoder;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

class ExcelFileDownloadLink extends DownloadLink {

    private static final long serialVersionUID = 1L;
    
    private final String xlsxFileName;

    public ExcelFileDownloadLink(String id, LoadableDetachableModel<File> model, String xlsxFileName) {
        super(id, model, xlsxFileName);
        this.xlsxFileName = xlsxFileName;
    }

    @Override
    public void onClick()
    {
        final File file = getModelObject();
        if (file == null)
        {
            throw new IllegalStateException(getClass().getName() +
                " failed to retrieve a File object from model");
        }

        String fileName = UrlEncoder.QUERY_INSTANCE.encode(this.xlsxFileName, getRequest().getCharset());

        final IResourceStream resourceStream = new FileResourceStream(
            new org.apache.wicket.util.file.File(file)) {
            
            private static final long serialVersionUID = 1L;

            @Override
            public String getContentType() {
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml";
            }
        };
        
        getRequestCycle().scheduleRequestHandlerAfterCurrent(
            new ResourceStreamRequestHandler(resourceStream)
            {
                @Override
                public void respond(IRequestCycle requestCycle)
                {
                    super.respond(requestCycle);
                    Files.remove(file);
                }
            }.setFileName(fileName)
                .setContentDisposition(ContentDisposition.ATTACHMENT));
    }
    
    
}