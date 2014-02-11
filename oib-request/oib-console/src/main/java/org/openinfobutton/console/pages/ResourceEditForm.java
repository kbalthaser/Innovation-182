package org.openinfobutton.console.pages;


import edu.utah.further.profiledb.domain.Profiles;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.openinfobutton.console.services.KnowledgeResourceService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/11/14
 * Time:     9:31 PM
 */
public class ResourceEditForm extends Form {

    @Inject
    KnowledgeResourceService service;

    boolean isNew = false;

    public Long id;
    public String name;
    public int version;
    public String publishDate;
    //status 1=active; 2=under development; 3=inactive
    public String status;

    private Profiles profile;



    public ResourceEditForm(String id) {

        super(id);


        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new TextField("version"));
        add(new TextField("publishDate"));
        List<String> statusOptions = Arrays.asList("Active", "Under Development", "Inactive");
        add(new DropDownChoice<String>("status", statusOptions));

        // Add a cancel button
        Button cancel = new Button("cancelButton") {

            public void onSubmit() {

                setResponsePage(ResourceListPage.class);
            }
        };

        cancel.setDefaultFormProcessing(false);
        add(cancel);

        // Setup a button for deleting the profile
        Button delete = new Button("deleteButton") {

            public void onSubmit() {

                // Remove this profile
                service.deleteProfile(profile.getId());
                setResponsePage(ResourceListPage.class);
            }
        };

        delete.setDefaultFormProcessing(false);
        add(delete);
    }



    public final void onSubmit() {

        profile.setName(name);
        profile.setVersion(version);
        profile.setPublished(new Timestamp(new Date().getTime())); //update the published date every update?

        if (status.equalsIgnoreCase("Active")) {
            profile.setStatus(1);
        } else if (status.equalsIgnoreCase("Under Development")) {
            profile.setStatus(2);
        } else {
            profile.setStatus(3);
        }

        service.saveProfile(profile);

        setResponsePage(ResourceListPage.class);
    }



    protected void loadProfile() {

        if (!isNew) {
            profile = service.getProfile(id);

            name = profile.getName();
            version = profile.getVersion();
            publishDate = profile.getPublished().toString();

            switch (profile.getStatus()) {
                case 1:
                    status = "Active";
                    break;
                case 2:
                    status = "Under Development";
                    break;
                default:
                    status = "Inactive";
            }
        } else {
            profile = new Profiles();
            profile.setPublished(new Timestamp(new Date().getTime()));
            publishDate = profile.getPublished().toString();
        }
    }



    public void setId(Long id) {

        this.id = id;
        loadProfile();
    }



    public void setIsNew(boolean newFlag) {

        isNew = newFlag;
        loadProfile();
    }
}
