package org.openinfobutton.console.pages;


import edu.utah.further.profiledb.domain.Profiles;
import org.apache.log4j.Logger;
import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.util.io.IOUtils;
import org.openinfobutton.console.services.KnowledgeResourceService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/12/14
 * Time:     2:36 PM
 *
 * Resource page handling AJAX operations for the XML knowledge profile definitions.
 *
 * If the request is a POST, we will take what was posted, and save it. Otherwise we simply return the
 * XML content for the requested ID.
 *
 * The resource should be mounted and made available via a path in the application class.
 */
public class XmlDownloadPage extends AbstractResource {

    @Inject
    KnowledgeResourceService service;

    @Inject
    Logger logger;



    public XmlDownloadPage() {

        // Wicket-CDI Can't directly inject into here, ask the container to do it for us
        CdiContainer.get().getNonContextualManager().inject(this);
    }



    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {

        HttpServletRequest request = (HttpServletRequest)attributes.getRequest()
                .getContainerRequest();
        final long id = attributes.getParameters().get("id").toLong();
        String xml = "";

        // Check and see if this is a post from the Editor, if so, extract out the XML that was posted, and save it
        if (request.getMethod().equalsIgnoreCase("POST")) {

            try {
                xml = IOUtils.toString(request.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // If not null, save it
            if (xml != null) {
                service.saveProfileXml(id, xml);
            }
        }

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("text/xml");
        resourceResponse.setTextEncoding("utf-8");

        resourceResponse.setWriteCallback(new WriteCallback() {

            @Override
            public void writeData(Attributes attributes) throws IOException {

                OutputStream outputStream = attributes.getResponse().getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream);
                String text = "";
                try {

                    Profiles profile = service.getProfile(id);
                    byte[] bdata = profile.getContent().getBytes(1,
                            (int)profile.getContent().length());
                    text = new String(bdata, "UTF-8");
                } catch (Exception ex) {
                    logger.error("Couldn't convert blob to string");
                }

                writer.write(text);
                writer.flush();
            }
        });

        return resourceResponse;
    }
}
