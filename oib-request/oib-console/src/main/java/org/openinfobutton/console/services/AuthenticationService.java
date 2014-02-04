package org.openinfobutton.console.services;


import javax.enterprise.context.ApplicationScoped;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     1:10 PM
 *
 *  Service to provide authentication logic
 */

@ApplicationScoped
public class AuthenticationService {

    public boolean isAuthenticated(String username, String password) {

        //TODO plug in user/pass authentication logic

        return true;
    }

}
