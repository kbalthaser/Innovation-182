package org.openinfobutton.console.services;


import edu.utah.further.profiledb.domain.Profiles;
import org.apache.log4j.Logger;
import org.openinfobutton.console.dao.KnowledgeResourceDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     3:47 PM
 *
 * Service layer for working with profiles.  Provides a layer in between our raw DAO and the outside world
 *
 * This layer should be used as a wrapper to perform all operations against the database
 */

@RequestScoped
public class KnowledgeResourceService {

    @Inject
    protected KnowledgeResourceDAO dao;

    @Inject
    transient Logger logger;



    /**
     * Return the current number of profiles stored in the database. If no profiles are available, zero is returned
     *
     * @return The number of profiles. If none, 0 is returned
     */
    public int getNumberOfProfiles() {

        return dao.getNumberOfProfiles();
    }



    /**
     * Returns a subset of ALL profiles starting from "start" and running to include the "max" number of profiles.
     *
     * @param max Maximum number of results to be returned
     * @param start The starting point for this search.
     * @return List containing the requested results.  If no results are found, an Empty list is returned.
     */
    public List<Profiles> findProfiles(int max, int start) {

        List<Profiles> profiles = dao.findProfiles(max, start);

        if (profiles == null) {
            profiles = new ArrayList<Profiles>();
        }

        return profiles;
    }



    /**
     * Return the profile identified by the identifier "id".  If no profile is found, null is returned
     *
     * @param id identifier for the requested profile
     * @return Profiles entity for the requested profile.  Null if no profile is found.
     */
    public Profiles getProfile(long id) {

        return dao.getProfile(id);
    }



    /**
     * Save the provided profile to the database.  If the provided profile does NOT have an XML content, a skeleton xml profile will be added.
     * If the provided profile has not yet been persisted, it will be ADDED to the database.  If the entity is derived from a previously persisted item it will be UPDATED.
     *
     * @param profile The profile to be updated/created
     */
    public void saveProfile(Profiles profile) {

        try {
            if (profile.getContent() == null) {
                // add content
                profile.setContent(new SerialBlob(
                        "<knowledgeResourceProfile xsi:noNamespaceSchemaLocation=\"../../models/ResourceProfile-v2.8_LITE.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></knowledgeResourceProfile>"
                                .getBytes()));
            }
        } catch (Exception ex) {
            logger.warn("Unable to add default content to profile. " + ex.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
        }

        dao.saveProfile(profile);
    }



    /**
     * Saves the profile XML definition for the profile identified by "id".  If an error occurs, an IllegalStateException is thrown.
     *
     * @param id Id value for the profile this XML belongs to.
     * @param profileXml String containing XML to be saved to the profile represented by "id"
     */
    public void saveProfileXml(long id, String profileXml) throws IllegalStateException {

        try {
            Profiles profile = dao.getProfile(id);
            profile.setContent(new SerialBlob(profileXml.getBytes()));
            dao.saveProfile(profile);
        } catch (Exception ex) {
            logger.error("Couldn't save XML profile. ", ex);
            throw new IllegalStateException("Profile XML could not be saved.");
        }

    }



    /**
     * Remove the requested profile from the database.
     *
     * @param id Id for the profile to be removed
     */
    public void deleteProfile(long id) {

        Profiles profile = dao.getProfile(id);
        dao.deleteProfile(profile);
    }
}
