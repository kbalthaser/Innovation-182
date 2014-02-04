package org.openinfobutton.console.dao;


import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     3:52 PM
 *
 * Base class for DAO's.  Sets up the container managed transaction and provides an
 * entity manager instance from the container.
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BaseDAO {

    @Inject
    EntityManager em;

}
