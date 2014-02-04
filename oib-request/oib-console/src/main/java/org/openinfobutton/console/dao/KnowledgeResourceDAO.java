package org.openinfobutton.console.dao;


import edu.utah.further.profiledb.domain.Profiles;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     7:34 PM
 *
 * EJB Providing the DAO for working with profiles.  This DAO utilizes container managed transactions for simplicity.
 *
 * This class should not be accessed directly where possible.  Please use the KnowledgeResourceService instead, as this provides
 * the business logic friendly layer
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KnowledgeResourceDAO extends BaseDAO {

    @Inject
    transient Logger logger;



    public int getNumberOfProfiles() {

        int rtn = 0;
        try {
            Query criteria = em.createQuery("SELECT count(p) FROM Profiles p");

            Number res = (Number)criteria.getSingleResult();

            rtn = res.intValue();

        } catch (Exception e) {
            logger.warn("Error receiving list of Profiles. " + e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
        }

        return rtn;
    }



    public List<Profiles> findProfiles(int max, int start) {

        List<Profiles> rtn = null;
        try {
            TypedQuery<Profiles> criteria = em.createQuery("SELECT p FROM Profiles p",
                    Profiles.class);
            criteria.setFirstResult(start);
            criteria.setMaxResults(max);

            rtn = criteria.getResultList();
        } catch (Exception e) {
            logger.warn("Error retrieving list of Profiles. " + e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
        }

        return rtn;
    }



    public Profiles getProfile(long id) {

        Profiles rtn = null;
        try {
            rtn = em.find(Profiles.class, id);
        } catch (Exception e) {
            logger.warn("Error retrieving Profile by key. " + e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
        }

        return rtn;
    }



    public void saveProfile(Profiles profile) {

        try {
            em.merge(profile);
        } catch (Exception e) {
            logger.warn("Error saving profile. " + e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
        }
    }



    public void deleteProfile(Profiles profile) {

        // This profile must not be detached
        profile = em.merge(profile);
        em.remove(profile);
    }
}
