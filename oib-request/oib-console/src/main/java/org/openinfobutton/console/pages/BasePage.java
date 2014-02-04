package org.openinfobutton.console.pages;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/11/14
 * Time:     8:03 PM
 *
 * Base page for this application.  Handles our template for headers and footers
*/


public class BasePage extends WebPage {

    public BasePage() {

        // Setup nav links for all pages
        add(new Link<Void>("LogOff") {

            @Override
            public void onClick() {

                setResponsePage(LogOff.class);
            }
        });

        add(new Link<Void>("ResourceList") {

            @Override
            public void onClick() {

                setResponsePage(ResourceListPage.class);
            }
        });

        add(new Link<Void>("Index") {

            @Override
            public void onClick() {

                setResponsePage(IndexPage.class);
            }
        });
    }
}
