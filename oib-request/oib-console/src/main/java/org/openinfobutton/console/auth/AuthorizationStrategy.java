package org.openinfobutton.console.auth;


import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.openinfobutton.console.pages.LoginPage;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     1:19 PM
 *
 * Setup Auth strategy for Wicket.  If a the component has the AuthenticatedPage annotation,
 * we will force a check of the session to ensure that the user is signed in.
 *
 */

public class AuthorizationStrategy implements IAuthorizationStrategy {

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
            Class<T> componentClass) {

        if (componentClass.isAnnotationPresent(AuthenticatedPage.class)) {
            if (((UserSession)Session.get()).isSignedIn()) { return true; }

            throw new RestartResponseAtInterceptPageException(LoginPage.class);
        }

        return true;
    }



    @Override
    public boolean isActionAuthorized(Component component, Action action) {

        return true;
    }
}
