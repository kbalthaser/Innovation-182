package org.openinfobutton.console.auth;


import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.openinfobutton.console.services.AuthenticationService;

import javax.inject.Inject;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     1:07 PM
 */


public class UserSession extends AuthenticatedWebSession {

    @Inject
    protected AuthenticationService authService;

    private String username;



    public UserSession(Request request) {

        super(request);
    }



    @Override
    public boolean authenticate(String username, String password) {

        if (authService.isAuthenticated(username, password)) {
            this.username = username;
            return true;
        }

        return false;
    }



    @Override
    public Roles getRoles() {

        if (isSignedIn()) { return new Roles(Roles.ADMIN); }

        return null;
    }



    @Override
    public void signOut() {

        super.signOut();
    }



    public String getUsername() {

        return username;
    }
}
