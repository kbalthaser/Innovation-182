package org.openinfobutton.console.auth;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Marker annotation for pages which require authentication
 *
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     12:52 PM
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedPage {
}
