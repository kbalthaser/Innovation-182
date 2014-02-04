package org.openinfobutton.console.pages;


import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     12:53 PM
 */
public class LoginPage extends WebPage {

    public LoginPage(final PageParameters parameters) {

        add(new SignInPanel("signInPanel", false));

    }

}
