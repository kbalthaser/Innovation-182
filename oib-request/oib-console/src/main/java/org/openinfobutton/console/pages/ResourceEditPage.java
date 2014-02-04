package org.openinfobutton.console.pages;


import org.openinfobutton.console.auth.AuthenticatedPage;


/**
 * User:     Kevin Balthaser
 * Contact:  kbalthaser@getwellnetwork.com
 * Date:     1/11/14
 * Time:     8:57 PM
 */

@AuthenticatedPage
public class ResourceEditPage extends BasePage {

    private String resourceId;



    public ResourceEditPage(long id) {

        resourceId = Long.toString(id);
        ResourceEditForm form = new ResourceEditForm("editForm");
        form.setId(id);
        form.setIsNew(false);
        add(form);
    }



    public ResourceEditPage() {

        ResourceEditForm form = new ResourceEditForm("editForm");
        form.setIsNew(true);
        add(form);
    }



    public String getResourceId() {

        return resourceId;
    }



    public void setResourceId(String resourceId) {

        this.resourceId = resourceId;
    }


}
