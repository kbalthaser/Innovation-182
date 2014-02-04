package org.openinfobutton.console.pages;


import edu.utah.further.profiledb.domain.Profiles;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.openinfobutton.console.model.ProfileDataProvider;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/10/14
 * Time:     7:48 PM
 */
public class ResourceListPage extends BasePage {

    public ResourceListPage() {

        setVersioned(false);

        DataView<Profiles> dataView = new DataView<Profiles>("rows", new ProfileDataProvider()) {

            @Override
            protected void populateItem(Item<Profiles> item) {

                final Profiles profile = item.getModelObject();

                item.setModel(new CompoundPropertyModel<Profiles>(profile));

                item.add(new Link<ResourceEditForm>("edit") {

                    @Override
                    public void onClick() {

                        setResponsePage(new ResourceEditPage(profile.getId()));
                    }
                });

                item.add(new Link<ResourceXmlEditPage>("editXml") {

                    @Override
                    public void onClick() {

                        setResponsePage(ResourceXmlEditPage.class,
                                getPageParameters().add("id", profile.getId()));
                    }
                });

                item.add(new Label("id"));
                item.add(new Label("name"));
                item.add(new Label("published"));

            }
        };

        dataView.setItemsPerPage(10l);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));

        add(new Link<ResourceEditForm>("newProfile") {

            @Override
            public void onClick() {

                setResponsePage(new ResourceEditPage());
            }
        });


    }
}
