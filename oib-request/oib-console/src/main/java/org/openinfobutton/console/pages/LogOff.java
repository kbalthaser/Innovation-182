package org.openinfobutton.console.pages;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     2:22 PM
 */

public class LogOff extends WebPage {

    public LogOff(final PageParameters parameters) {

        // Clear session, kick to login page

        getSession().invalidate();
        getRequestCycle().setResponsePage(LoginPage.class);
    }

}
