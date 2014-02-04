package org.openinfobutton.console.model;


import edu.utah.further.profiledb.domain.Profiles;
import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.openinfobutton.console.services.KnowledgeResourceService;

import javax.inject.Inject;
import java.util.Iterator;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     8:22 PM
 *
 * Build the data model for our profiles. Used to populate the data view of profiles
 */
public class ProfileDataProvider implements IDataProvider<Profiles> {

    @Inject
    KnowledgeResourceService service;



    public ProfileDataProvider() {

        // Wicket-CDI Can't directly inject into here, ask the container to do it for us
        CdiContainer.get().getNonContextualManager().inject(this);
    }



    @Override
    public Iterator< ? extends Profiles> iterator(long first, long count) {

        return service.findProfiles((int)count, (int)first).iterator();
    }



    @Override
    public long size() {

        return service.getNumberOfProfiles();
    }



    @Override
    public IModel<Profiles> model(final Profiles profile) {

        return new LoadableDetachableModel<Profiles>() {

            @Override
            protected Profiles load() {

                return profile;
            }
        };
    }



    @Override
    public void detach() {

    }
}
