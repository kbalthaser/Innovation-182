package org.openinfobutton.console.factory;


import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     4:05 PM
 *
 * Producer class for the main entity manager.  Entity manager is set to use our
 * default persistence context.
 */
public class PersistenceFactory {

    @PersistenceContext(unitName = "oib-console")
    @Produces
    EntityManager em;
}
