package org.openinfobutton.console;


import de.agilecoders.wicket.webjars.collectors.VfsJarAssetPathCollector;
import de.agilecoders.wicket.webjars.util.WicketWebjars;
import org.apache.wicket.Session;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.jboss.weld.environment.servlet.Listener;
import org.openinfobutton.console.auth.AuthorizationStrategy;
import org.openinfobutton.console.auth.UserSession;
import org.openinfobutton.console.pages.IndexPage;
import org.openinfobutton.console.pages.XmlDownloadPage;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     11:39 AM
 */
public class AdminConsoleApplication extends WebApplication {

    @Override
    public Class getHomePage() {

        return IndexPage.class;
    }



    /**
     * Setup Web Jars to pull in bootstrap
     */
    @Override
    public void init() {

        super.init();

        // Install WebJars
        WicketWebjars.install(this);
        WicketWebjars.registerCollector(new VfsJarAssetPathCollector());

        // Setup security & auth
        getSecuritySettings().setAuthorizationStrategy(new AuthorizationStrategy());

        // configure Wicket/CDI
        BeanManager manager = null;
        try {
            // lookup bean manager from Weld's servlet listener
            manager = (BeanManager)getServletContext().getAttribute(
                    Listener.BEAN_MANAGER_ATTRIBUTE_NAME);

            if (manager == null) {
                // we are probably running on JBoss or similar
                manager = (BeanManager)new InitialContext().lookup("java:comp/BeanManager");
            }
        } catch (NamingException e) {
        }


        new CdiConfiguration(manager).configure(this);

        // Make the profile XML available via a path
        ResourceReference resourceReference = new ResourceReference("xmlPage") {

            XmlDownloadPage xmlPage = new XmlDownloadPage();



            @Override
            public IResource getResource() {

                return xmlPage;
            }
        };


        mountResource("/profileXml", resourceReference);


    }



    @Override
    public Session newSession(Request request, Response response) {

        return new UserSession(request);
    }

}
